package com;

import com.ControlDependence.AlwaysTaken;
import com.ControlDependence.ChooseBranchPrediction;
import com.ControlDependence.Stalling;
import com.Cpu.*;
import com.CpuOutput.*;
import com.DataDependence.ForwardingUnit;
import com.Latch.*;
import com.Memory.Global;

import java.io.IOException;
import java.util.Objects;

import static com.Main.*;

public class ProcessUnit extends ManageLatches {

    public static int process(String path) throws IOException {

        Global.initializedRegister();

        Logger.println("\n--------------pipeline Start--------------");
        Logger.println("--------------" + path + " --------------\n");

        //Fetch 선언 및 Instruction Fetch
        MemoryFetch memoryFetch = new MemoryFetch(path);

        //객체 생성 - 하드웨어 컴포넌트 생성
        Decode decode = new Decode();
        Register register = new Register();
        PC pcUpdate = new PC();
        ALU alu = new ALU();
        Memory memory = new Memory();
        ForwardingUnit forwardingUnit = new ForwardingUnit();

        //branch prediction strategy
        AlwaysTaken alwaysTaken = new AlwaysTaken();

        //Latch 선언
        ManageLatches.declareLatches();

        //변수 초기화
        int cycleCount = 1;
        int pc = 0;
        boolean fetchValid = true;
        boolean instEndPoint = false;
        boolean inputInstEndPoint = false;
        initializeValidBits(if_id, id_exe, exe_mem, mem_wb);

        while (!mem_wb.instEndPoint) {

            //total cycle 수 측정
            if (cycleCount % 1000000 == 0) {
                Logger.countPrintln("Cycle Count : %d\n", cycleCount++);
            }

            Logger.setPrintRange(cycleCount);

            //Todo : --------------------------------------Start WriteBack Stage------------------------------------
            //MemToReg 값 구분 MUX
            int memToRegValue = register.memToRegSet(mem_wb.valid, mem_wb.controlSignal, mem_wb.memoryCalcResult, mem_wb.finalAluResult);
            register.registerWrite(mem_wb.mem_wbPc, mem_wb.valid, mem_wb.controlSignal, memToRegValue, mem_wb.regDst);

            //--------------------------------------Finish WriteBack Stage------------------------------------

            //Todo : -----------------------------------Start MemoryAccess Stage------------------------------------
            MemoryOutput memoryOutput = memory.read(exe_mem.valid, exe_mem.finalAluResult, exe_mem.controlSignal, exe_mem.instEndPoint);
            memory.write(exe_mem.valid, exe_mem.finalAluResult, exe_mem.rtValue, exe_mem.controlSignal, exe_mem.instEndPoint);

            //-----------------------------------Finish MemoryAccess Stage------------------------------------

            //Todo : -----------------------------------------Start Fetch Stage--------------------------------------
            MemoryFetchOutput memoryFetchOutput = memoryFetch.fetch(fetchValid, pc);
            String pcHex = Integer.toHexString(pc*4);
            int currentStagePc = pc; //Todo: 업데이트 전 현재 pc값 저장

            //------------------------------------Finish Fetch Stage------------------------------------
            //Latch
            if_id.input(fetchValid, pc, memoryFetchOutput.instruction, memoryFetchOutput.hexInstruction);

            //Todo : -----------------------------------------pc Update----------------------------------------
            if(fetchValid) {
                pc = pc + 1;
            }

            //Todo : ------------------------------------Start Decode Stage------------------------------------
            DecodeOutput decodeOutput = decode.decodeInstruction(if_id.valid, if_id.instruction, if_id.if_idPc);
            RegisterOutput registerOutput = register.registerCalc(if_id.valid, decodeOutput.rs,
                    decodeOutput.rt, decodeOutput.controlSignal);

            //Todo : ------------------------------------Data forwarding 처리--------------------------------------

            int forwardA = forwardingUnit.forward(exe_mem.valid, mem_wb.valid, exe_mem.controlSignal, mem_wb.controlSignal,
                    exe_mem.regDstValue, id_exe.rs, mem_wb.regDst);

            int forwardB = forwardingUnit.forward(exe_mem.valid, mem_wb.valid, exe_mem.controlSignal, mem_wb.controlSignal,
                    exe_mem.regDstValue, id_exe.rt, mem_wb.regDst);

            // forwardA･B MUX
            id_exe.readData1 = forwardMux(forwardA, id_exe.readData1, exe_mem.finalAluResult, memToRegValue);
            id_exe.readData2 = forwardMux(forwardB, id_exe.readData2, exe_mem.finalAluResult, memToRegValue);

            //---------------------------------------------Finish Decode Stage--------------------------------------

            // nextPc값 생성
            int nextPc = getNextPc(pc, decodeOutput);

            //Latch
            id_exe.input(if_id.valid, decodeOutput.controlSignal, if_id.if_idPc, registerOutput.firstRegisterOutput, registerOutput.secondRegisterOutput,
                    decodeOutput.signExt, decodeOutput.zeroExt, decodeOutput.shamt, decodeOutput.jumpAddr, decodeOutput.branchAddr,
                    decodeOutput.loadUpperImm, decodeOutput.rs, decodeOutput.rt, decodeOutput.rd, nextPc, instEndPoint);

            //Todo : ------------------------------------Start Execution Stage--------------------------------------
            //RegDst 값 구하기
            int regDstResult = decodeOutput.regDstSet(id_exe.valid, id_exe.controlSignal, id_exe.rt, id_exe.rd);
            int aluInput1 = alu.setAluInput1(id_exe.valid, id_exe.controlSignal, id_exe.shamt, id_exe.readData1);
            int aluInput2 = alu.setAluInput2(id_exe.valid, id_exe.controlSignal, id_exe.signExt, id_exe.zeroExt, id_exe.readData2);

            AluOutput aluOutput = alu.process(id_exe.valid, id_exe.controlSignal, aluInput1, aluInput2);

            //------------------------------------Finish Execution Stage------------------------------------

            //Todo : ------------------------------------ Start pc update --------------------------------------
            //------------------------------------------JAL / JUMP /JR pc update------------------------------------------
            pc = pcUpdate.pcJumpJalJrUpdate(if_id.valid, pc, decodeOutput.controlSignal,
                    id_exe.inputReadData1, decodeOutput.jumpAddr);

            //------------------------------------------BNE / BEQ pc update------------------------------------------
//           Todo : -------------------------------Control Dependence 처리 --------------------------------------
            if(ChooseBranchPrediction.onAlwaysTaken) {
                pc =pcUpdate.AlwaysTakenPcUpdate(if_id.valid, decodeOutput.controlSignal, pc, nextPc);
                if(id_exe.valid) {
                    if(Objects.equals(id_exe.controlSignal.inst, "BNE") ||
                            Objects.equals(id_exe.controlSignal.inst, "BEQ"))
                        if(!(alwaysTaken.taken() == pcUpdate.bneBeqProcess(aluOutput.aluResult, id_exe.controlSignal))) {
                            if_id.inputValid = false;
                            pc = id_exe.id_exePc + 2;
                        }
                    }
            }

            else if(ChooseBranchPrediction.onAlwaysNotTaken) {

            }
            else {
                pc = pcUpdate.bneBeqPcUpdate(id_exe.valid, id_exe.controlSignal, pc ,id_exe.id_exePc, aluOutput.aluResult, id_exe.branchAddr);
                fetchValid = Stalling.stallingMethod(if_id.valid, fetchValid, decodeOutput, aluOutput); //Todo: Stalling
            }
            //-----------------------------------------finish pc update -----------------------------------------

            //loadUpper값 구분 Mux(LUI)
            int finalAluResult = alu.setAddress(id_exe.valid, id_exe.controlSignal, id_exe.loadUpper, aluOutput.aluResult);
            //Latch
            exe_mem.input(id_exe.valid, id_exe.controlSignal, id_exe.id_exePc, id_exe.readData2,
                    finalAluResult, regDstResult, id_exe.instEndPoint);


//            Todo : ---------------------------Control Dependence 처리 : Always Taken --------------------------------------
//            if(id_exe.valid) {
//                System.out.println(currentStagePc);
//                System.out.println(id_exe.nextPc);
//                System.out.println(nextPc);
//            }

            //------------------------------------------Finish Control Dependence------------------------------------------
            //Latch
            mem_wb.input(exe_mem.valid, exe_mem.exe_memPc, exe_mem.controlSignal,
                    exe_mem.finalAluResult, memoryOutput.memoryCalcResult, exe_mem.regDstValue, exe_mem.instEndPoint);

            //Todo : -----------------------------------pc == -1 일때 처리 -----------------------------------------

            if (pc == -1) {
                fetchValid = false;
                if_id.inputValid = false;
                inputInstEndPoint = true;
            }

            printLogo(fetchValid, cycleCount++, pcHex, decodeOutput, registerOutput, finalAluResult, pc,
                    register, if_id, id_exe, exe_mem, mem_wb, memoryFetchOutput, aluOutput, pcUpdate, memory);

            //latch Update
            flush();

            //EndPoint Update
            instEndPoint = inputInstEndPoint;
        }

        System.out.println("\n\n-------------------------- Finish Program --------------------------");
        System.out.println("total count is " + cycleCount);
        System.out.printf("result value R[2] : %d\n", Global.register[2]);
        return Global.register[2];
    }

    public static int getNextPc(int pc, DecodeOutput decodeOutput) {
        //nextPc값 확인
        int nextPc = pc;
        if(if_id.valid) {
            switch (decodeOutput.controlSignal.inst) {

                case "JUMP" :
                case "JAL" : {
                    nextPc = decodeOutput.jumpAddr / 4;
                } break;

                case "JR" : {
                    nextPc = id_exe.inputReadData1;
                } break;

                case "BNE" :
                case "BEQ" : {
                    nextPc = ((if_id.if_idPc+1) * 4 + decodeOutput.branchAddr) / 4;
                }  break;
            }
        }
        return nextPc;
    }
}
