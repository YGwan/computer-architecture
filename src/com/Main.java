package com;

import com.Cpu.*;
import com.CpuOutput.*;
import com.Latch.EXE_MEM;
import com.Latch.ID_EXE;
import com.Latch.IF_ID;
import com.Latch.MEM_WB;
import com.Memory.Global;

import java.io.IOException;
import java.util.Objects;

public class Main extends Global {

    public static void main(String[] args) throws IOException {
        Logger.LOGGING_SIGNAL = true;
        Logger.LOGGING_COUNTER_SIGNAL = true;

//        test("source/simple.bin", 0);
//        test("source/simple2.bin", 100);
        test("source/simple3.bin", 5050);
//        test("source/simple4.bin", 55);
//        test("source/gcd.bin", 1);
//        test("source/fib.bin", 55);
//        test("source/input4.bin", 85);
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
        Global.init();

        Logger.println("\n--------------pipeline Start--------------");
        Logger.println("--------------" + path + " --------------\n");

        //Fetch 선언 및 Instruction Fetch
        MemoryFetch memoryFetch = new MemoryFetch(path);
        //memoryFetch.printBitInstruction();
        //memoryFetch.printHexInstruction();

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

        boolean instEndPoint = false;

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

            if(cycleCount > 100) {
                Logger.LOGGING_SIGNAL = false;
                Logger.LOGGING_COUNTER_SIGNAL = false;
            }
            //Todo : --------------------------------------Start WriteBack Stage------------------------------------

            //MemToReg 값 구분 MUX
            int memToRegValue = register.memToRegSet(mem_wb.valid, mem_wb.controlSignal, mem_wb.memoryCalcResult, mem_wb.finalAluResult);

            //writeBack
            register.registerWrite(mem_wb.nextPc, mem_wb.valid, mem_wb.controlSignal, memToRegValue, mem_wb.regDst);

            //--------------------------------------Finish WriteBack Stage------------------------------------

            //Todo : -----------------------------------Start MemoryAccess Stage------------------------------------

            //loadUpper값 구분 Mux(LUI)
            int finalAluResult = memory.setAddress(exe_mem.valid, exe_mem.controlSignal, id_exe.loadUpper, exe_mem.aluResult);

            //Memory Access
            MemoryOutput memoryOutput = memory.read(exe_mem.valid, exe_mem.aluResult, exe_mem.controlSignal, exe_mem.instEndPoint);

            memory.write(exe_mem.valid, exe_mem.aluResult, exe_mem.rtValue, exe_mem.controlSignal, exe_mem.instEndPoint);


            //-----------------------------------Finish MemoryAccess Stage------------------------------------


            //Todo : -----------------------------------------Start Fetch Stage--------------------------------------

            // instruction fetch


            //Todo: pc가 -1로 갈때, IndexOutOfBoundsException 생기는 것을 방지하기 위해서 씀
            MemoryFetchOutput memoryFetchOutput = null;
            String pcHex = null;
            try {
                memoryFetchOutput = memoryFetch.fetch(fetchValid, pc);
                pcHex = Integer.toHexString(pc * 4);
                pc = nextPC;

            } catch (IndexOutOfBoundsException ignored) {
                for (int i = 0; i < 2; i++) {
                    memoryFetchOutput = memoryFetch.fetch(fetchValid, pc - 1);
                    pcHex = Integer.toHexString(pc * 4);
                }
            }


            //------------------------------------Finish Fetch Stage------------------------------------

            //Latch
            if_id.input(fetchValid, pc-1, memoryFetchOutput.instruction, memoryFetchOutput.hexInstruction);


            //Todo : ------------------------------------Start Decode Stage------------------------------------

            // instruction decode
            DecodeOutput decodeOutput = decode.decodeInstruction(if_id.valid, if_id.instruction);

            RegisterOutput registerOutput = register.registerCalc(if_id.valid, decodeOutput.rs,
                    decodeOutput.rt, decodeOutput.controlSignal);


            //------------------------------------Finish Decode Stage--------------------------------------

            //Todo: jump, jal, bne,beq일때 fetchvaild false 만들기

            if (if_id.valid) {
                if (Objects.equals(decodeOutput.controlSignal.inst, "JUMP") ||
                        Objects.equals(decodeOutput.controlSignal.inst, "JAL")) {
                    fetchValid = false;
                } else {
                    fetchValid = true;
                }
            }


            //Latch
            id_exe.input(if_id.valid, decodeOutput.controlSignal, if_id.nextPC, registerOutput.firstRegisterOutput, registerOutput.secondRegisterOutput,
                    decodeOutput.signExt, decodeOutput.zeroExt, decodeOutput.shamt, decodeOutput.jumpAddr, decodeOutput.branchAddr,
                    decodeOutput.loadUpperImm, decodeOutput.rs, decodeOutput.rt, decodeOutput.rd);

            //RegDst 값 구하기
            int regDstResult = decodeOutput.regDstSet(id_exe.valid, id_exe.controlSignal, id_exe.rt, id_exe.rd);

            //Todo : ------------------------------------Data forwarding 처리--------------------------------------


            //Execution
            int signalA = forwardingUnit.forwardA(exe_mem.valid, mem_wb.valid, exe_mem.controlSignal, mem_wb.controlSignal,
                    exe_mem.regDstValue, id_exe.rs, mem_wb.regDst);

            int signalB = forwardingUnit.forwardB(exe_mem.valid, mem_wb.valid, exe_mem.controlSignal, mem_wb.controlSignal,
                    exe_mem.regDstValue, id_exe.rt, mem_wb.regDst, id_exe.rs);

            // forwardA･B MUX
            id_exe.readData1 = forwardMux(signalA, id_exe.readData1, exe_mem.aluResult, memToRegValue);
            id_exe.readData2 = forwardMux(signalB, id_exe.readData2, exe_mem.aluResult, memToRegValue);
            exe_mem.inputRtValue = id_exe.readData2;

            int aluInput1 = alu.setAluInput1(id_exe.valid, id_exe.controlSignal, id_exe.shamt, id_exe.readData1);
            int aluInput2 = alu.setAluInput2(id_exe.valid, id_exe.controlSignal, id_exe.signExt, id_exe.zeroExt, id_exe.readData2);

            //Todo : ------------------------------------Start Execution Stage------------------------------------

            AluOutput aluOutput = alu.process(id_exe.valid, id_exe.controlSignal, aluInput1, aluInput2);


            //------------------------------------Finish Execution Stage------------------------------------

            //Latch
            exe_mem.input(id_exe.valid, id_exe.controlSignal, id_exe.nextPc, id_exe.readData2,
                    aluOutput.aluResult, regDstResult, instEndPoint);

            if(if_id.valid) {
                if(Objects.equals(decodeOutput.controlSignal.inst, "BNE")) {
                    if(aluOutput.aluResult == 1) {
                        fetchValid = false;
                    }
                }
            }
            //Todo : ------------------------------------pc update 처리--------------------------------------
            pcUpdate.pcUpdate(id_exe.valid, id_exe.controlSignal, id_exe.nextPc, id_exe.readData1, exe_mem.aluResult, id_exe.jumpAddr, id_exe.branchAddr);


            //Todo : -----------------------------------pc == -1 일때 처리 -----------------------------------------

            if (pc == -1) {
                fetchValid = false;
                instEndPoint = true;
            }

            //Latch
            mem_wb.input(exe_mem.valid, exe_mem.nextPc, exe_mem.controlSignal, finalAluResult, memoryOutput.memoryCalcResult, exe_mem.regDstValue, exe_mem.instEndPoint);


            //Logo 출력
            memoryFetchOutput.printFetchStage(fetchValid, cycleCount, pcHex, if_id.inputHexInstruction);
            cycleCount++;
            registerOutput.printDecodeStage(if_id.valid, decodeOutput.opcode, decodeOutput.rs, decodeOutput.rt, registerOutput.firstRegisterOutput, registerOutput.secondRegisterOutput);
            aluOutput.printExecutionOutput(id_exe.valid, aluOutput.aluResult);
            pcUpdate.pcUpdatePrint(id_exe.valid);
            memory.printExecutionMemoryAccess(exe_mem.valid, exe_mem.controlSignal, exe_mem.aluResult, exe_mem.rtValue, exe_mem.instEndPoint);
            register.printExecutionWriteBack(mem_wb.valid, mem_wb.controlSignal, mem_wb.regDst, register.writeData);
            Logger.println();


            //latch Update
            if_id.output();
            id_exe.output();
            exe_mem.output();
            mem_wb.output();
        }

        System.out.printf("\nresult value R[2] : %d\n", Global.register[2]);
        return Global.register[2];
    }


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

    private static void initializedRegister() {
        pc = 0;
        register[29] = 0x1000000;
        register[31] = 0xFFFFFFFF;
    }

}
