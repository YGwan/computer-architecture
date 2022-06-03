package com;

import com.ControlDependence.Stalling;
import com.Cpu.*;
import com.CpuOutput.*;
import com.Latch.EXE_MEM;
import com.Latch.ID_EXE;
import com.Latch.IF_ID;
import com.Latch.MEM_WB;
import com.Memory.Global;

import java.io.IOException;

public class Main extends Global {

    public static void main(String[] args) throws IOException {
        Logger.LOGGING_SIGNAL = false;
        Logger.LOGGING_COUNTER_SIGNAL = false;

        Logger.min = 0;
        Logger.max = 1000;

//        test("source/simple.bin", 0);
//        test("source/simple2.bin", 100);
//        test("source/simple3.bin", 5050);
//        test("source/simple4.bin", 55);
//        test("source/gcd.bin", 1);
//        test("source/fib.bin", 55);
        test("source/input4.bin", 85);
    }

    private static void test(String path, int expect) throws IOException {
        int result = process(path);
        if (result != expect) {
            System.out.println("failed -> Path : " + path + " expected : " + expect + " real : " + result);
        }
        System.out.println("succeed -> Path : " + path + " expected : " + expect + " real : " + result);
    }


    private static int process(String path) throws IOException {

        initializedRegister();

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

        //Latch 선언
        IF_ID if_id = new IF_ID();
        ID_EXE id_exe = new ID_EXE();
        EXE_MEM exe_mem = new EXE_MEM();
        MEM_WB mem_wb = new MEM_WB();

        int cycleCount = 1;
        int pc = 0;

        boolean instEndPoint = false;
        boolean inputInstEndPoint = false;

        //valid 변수 초기화
        boolean fetchValid = true;
        if_id.valid = false;
        id_exe.valid = false;
        exe_mem.valid = false;
        mem_wb.valid = false;

        while (!mem_wb.instEndPoint) {

            //total cycle 수 측정
            if (cycleCount % 1000000 == 0) {
                Logger.countPrintln("Cycle Count : %d\n", cycleCount++);
            }

            Logger.setPrintRange(cycleCount);

            //Todo : --------------------------------------Start WriteBack Stage------------------------------------

            //MemToReg 값 구분 MUX
            int memToRegValue = register.memToRegSet(mem_wb.valid, mem_wb.controlSignal, mem_wb.memoryCalcResult, mem_wb.finalAluResult);

            //writeBack
            register.registerWrite(mem_wb.nextPc, mem_wb.valid, mem_wb.controlSignal, memToRegValue, mem_wb.regDst);

            //--------------------------------------Finish WriteBack Stage------------------------------------

            //Todo : -----------------------------------Start MemoryAccess Stage------------------------------------

            //Memory Access
            MemoryOutput memoryOutput = memory.read(exe_mem.valid, exe_mem.finalAluResult, exe_mem.controlSignal, exe_mem.instEndPoint);

            memory.write(exe_mem.valid, exe_mem.finalAluResult, exe_mem.rtValue, exe_mem.controlSignal, exe_mem.instEndPoint);

            //-----------------------------------Finish MemoryAccess Stage------------------------------------


            //Todo : -----------------------------------------Start Fetch Stage--------------------------------------

            // instruction fetch
            MemoryFetchOutput memoryFetchOutput = memoryFetch.fetch(fetchValid, pc);
            String pcHex = Integer.toHexString(pc * 4);

            //------------------------------------Finish Fetch Stage------------------------------------

            //Latch
            if_id.input(fetchValid, pc, memoryFetchOutput.instruction, memoryFetchOutput.hexInstruction);

            //Todo : -----------------------------------------pc Update----------------------------------------
            if(fetchValid) {
                pc = pc + 1;
            }

            //Todo : ------------------------------------Start Decode Stage------------------------------------

            // instruction decode
            DecodeOutput decodeOutput = decode.decodeInstruction(if_id.valid, if_id.instruction, if_id.nextPc);

            RegisterOutput registerOutput = register.registerCalc(if_id.valid, decodeOutput.rs,
                    decodeOutput.rt, decodeOutput.controlSignal);

            //Todo : ------------------------------------Data forwarding 처리--------------------------------------

            //Latch
            id_exe.input(if_id.valid, decodeOutput.controlSignal, if_id.nextPc, registerOutput.firstRegisterOutput, registerOutput.secondRegisterOutput,
                    decodeOutput.signExt, decodeOutput.zeroExt, decodeOutput.shamt, decodeOutput.jumpAddr, decodeOutput.branchAddr,
                    decodeOutput.loadUpperImm, decodeOutput.rs, decodeOutput.rt, decodeOutput.rd, instEndPoint);

            //Execution
            int forwardA = forwardingUnit.forward(exe_mem.valid, mem_wb.valid, exe_mem.controlSignal, mem_wb.controlSignal,
                    exe_mem.regDstValue, id_exe.rs, mem_wb.regDst);

            int forwardB = forwardingUnit.forward(exe_mem.valid, mem_wb.valid, exe_mem.controlSignal, mem_wb.controlSignal,
                    exe_mem.regDstValue, id_exe.rt, mem_wb.regDst);

            // forwardA･B MUX
            id_exe.readData1 = forwardMux(forwardA, id_exe.readData1, exe_mem.finalAluResult, memToRegValue);
            id_exe.readData2 = forwardMux(forwardB, id_exe.readData2, exe_mem.finalAluResult, memToRegValue);

            //---------------------------------------------Finish Decode Stage--------------------------------------

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
            pc = pcUpdate.bneBeqPcUpdate(id_exe.valid, id_exe.controlSignal, pc ,id_exe.nextPc, aluOutput.aluResult, id_exe.branchAddr);

            //-----------------------------------------finish pc update -----------------------------------------


            //loadUpper값 구분 Mux(LUI)
            int finalAluResult = memory.setAddress(id_exe.valid, id_exe.controlSignal, id_exe.loadUpper, aluOutput.aluResult);

            //Latch
            exe_mem.input(id_exe.valid, id_exe.controlSignal, id_exe.nextPc, id_exe.readData2,
                    finalAluResult, regDstResult, id_exe.instEndPoint);


            //Todo : --------------------------------Stalling Control Dependence 처리--------------------------------------

            fetchValid = Stalling.stallingMethod(if_id.valid, fetchValid, decodeOutput, aluOutput);

            //------------------------------------------Finish Control Dependence------------------------------------------

            //Latch
            mem_wb.input(exe_mem.valid, exe_mem.nextPc, exe_mem.controlSignal,
                    exe_mem.finalAluResult, memoryOutput.memoryCalcResult, exe_mem.regDstValue, exe_mem.instEndPoint);

            //Todo : -----------------------------------pc == -1 일때 처리 -----------------------------------------

            if (pc == -1) {
                fetchValid = false;
                if_id.inputValid = false;
                inputInstEndPoint = true;
            }

            //Logo 출력

            memoryFetchOutput.printFetchStage(fetchValid, cycleCount++, pcHex, if_id.inputHexInstruction);
            registerOutput.printDecodeStage(if_id.valid, decodeOutput.opcode, decodeOutput.rs, decodeOutput.rt, registerOutput.firstRegisterOutput, registerOutput.secondRegisterOutput);
            aluOutput.printExecutionOutput(id_exe.valid, finalAluResult);
            pcUpdate.pcUpdatePrint(id_exe.valid, pc);
            memory.printExecutionMemoryAccess(exe_mem.valid, exe_mem.controlSignal, exe_mem.finalAluResult, exe_mem.rtValue, exe_mem.instEndPoint);
            register.printExecutionWriteBack(mem_wb.valid, mem_wb.controlSignal, mem_wb.regDst, register.writeData);
            Logger.println();


            //latch Update
            if_id.output();
            id_exe.output();
            exe_mem.output();
            mem_wb.output();

            //EndPoint Update
            instEndPoint = inputInstEndPoint;
        }

        System.out.printf("cyl %d, IF Stage -> [NOP]\n" +
                "ID Stage -> [NOP]\n" +
                "EX Stage -> [NOP]\n" +
                "MA Stage -> [NOP]\n" +
                "WB Stage -> [NOP]\n", cycleCount);

        System.out.println("\n\n-------------------------- Finish Program --------------------------");
        System.out.println("total count is " + cycleCount);
        System.out.printf("result value R[2] : %d\n", Global.register[2]);

        return Global.register[2];
    }

//    public static void printLogo(boolean FetchValid, int cycleCount, String pcHex, IF_ID if_id,
//                                 DecodeOutput decodeOutput, RegisterOutput registerOutput, )

    public static int mux(boolean signal, int trueVal, int falseVal) {
        if (signal) {
            return trueVal;
        }
        return falseVal;
    }

    public static int forwardMux(int signal, int basic, int exe_memReturnVale, int mem_wbReturnValue) {

        int returnValue;

        switch (signal) {
            case 0:
                returnValue = basic;
                break;
            case 1:
                returnValue = exe_memReturnVale;
                break;
            case 2:
                returnValue = mem_wbReturnValue;
                break;

            default:
                returnValue = 0;
        }

        return returnValue;
    }

}
