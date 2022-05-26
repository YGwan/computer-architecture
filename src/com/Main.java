package com;

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

        Logger.LOGGING_SIGNAL = true;
        Logger.LOGGING_COUNTER_SIGNAL = true;

        initializedRegister();

        String path = "source/simple.bin";


        
        Logger.println("\n--------------pipeline Start--------------\n");

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

        //Latch 선언
        IF_ID if_id = new IF_ID();
        ID_EXE id_exe = new ID_EXE();
        EXE_MEM exe_mem = new EXE_MEM();
        MEM_WB mem_wb = new MEM_WB();

        int cycleCount = 1;

        boolean inputInstEndPoint = false;
        boolean instEndPoint = false;

        while (!mem_wb.instEndPoint) {

            //total cycle 수 측정
            if (cycleCount % 1000000 == 0) {
                Logger.countPrintln("Cycle Count : %d\n", cycleCount++);
            }

            //------------------------------------Start Fetch Stage------------------------------------

            // instruction fetch
            MemoryFetchOutput memoryFetchOutput = memoryFetch.fetch(pc);
            String pcHex = Integer.toHexString(pc * 4);
            pc = nextPC;

            //------------------------------------Finish Fetch Stage------------------------------------

            //Latch
            if_id.input(memoryFetchOutput.nextPC, memoryFetchOutput.instruction, memoryFetchOutput.hexInstruction);

            if (FetchValid) {
                Logger.println("cyl %d, IF Stage -> pc : 0x%s, instruction : 0x%s\n", cycleCount++, pcHex, if_id.inputHexInstruction);
            } else {
                Logger.println("cyl %d, IF Stage -> [NOP]\n", cycleCount++);
            }
            //------------------------------------Start Decode Stage------------------------------------

            // instruction decode
            DecodeOutput decodeOutput = decode.decodeInstruction(if_id.instruction);
            decodeOutput.printDecodeStage(decodeOutput.opcode, decodeOutput.rs, decodeOutput.rt);

            RegisterOutput registerOutput = register.registerCalc(decodeOutput.rs,
                    decodeOutput.rt, decodeOutput.controlSignal);

            //ALUSrc를 위해 signExt ,zeroExt, shamt보내기
            registerOutput.acceptSignExt(decodeOutput.signExt);
            registerOutput.acceptZeroExt(decodeOutput.zeroExt);
            registerOutput.acceptShamt(decodeOutput.shamt);


            //------------------------------------Finish Decode Stage--------------------------------------

            //Latch
            id_exe.input(decodeOutput.controlSignal, if_id.nextPC, registerOutput.firstRegisterOutput, registerOutput.secondRegisterOutput,
                    registerOutput.aluSrcResult, decodeOutput.regDstResult, decodeOutput.jumpAddr, decodeOutput.branchAddr, decodeOutput.loadUpperImm);


            //------------------------------------Data forwarding 처리--------------------------------------

            AluOutput aluOutput;

            if(onDataForwarding) {
                DataForwarding dataForwarding = new DataForwarding();



                int signalA = dataForwarding.forwardA(EXE_MEMValid, MEM_WBValid, exe_mem.controlSignal, mem_wb.controlSignal,
                        exe_mem.regDstValue, decodeOutput.rs, mem_wb.regDst);

                int signalB = dataForwarding.forwardB(EXE_MEMValid, MEM_WBValid, exe_mem.controlSignal, mem_wb.controlSignal,
                        exe_mem.regDstValue, decodeOutput.rt, mem_wb.regDst, decodeOutput.rs);

                // forwardA･B MUX
                int aluInputData1 = forwardMux(signalA, id_exe.readData1,exe_mem.aluCalcResult, mem_wb.memToRegResult);
                int aluInputData2 = forwardMux(signalB, id_exe.aluSrcResult,exe_mem.aluCalcResult, mem_wb.memToRegResult);
                aluOutput = alu.process(aluInputData1, aluInputData2, id_exe.controlSignal);

            }

            //------------------------------------Start Execution Stage------------------------------------

            //Execution
            else {
                aluOutput = alu.process(id_exe.readData1, id_exe.aluSrcResult, id_exe.controlSignal);
            }

            aluOutput.acceptLoadUpperImm(id_exe.loadUpper);
            aluOutput.printExecutionOutput();


            //pc Update
            pcUpdate.pcUpdate(id_exe.controlSignal, id_exe.readData1, aluOutput.aluCalcResult, id_exe.jumpAddr, id_exe.branchAddr);


            //-----------------------------------pc == -1 일때 처리 -----------------------------------------

            if (pc == -1) {
                Global.FetchValid = false;
                Global.InputIF_IDValid = false;
                Global.InputID_EXEValid = false;
                inputInstEndPoint = true;
            }


            //------------------------------------Finish Execution Stage------------------------------------

            //Latch
            exe_mem.input(id_exe.controlSignal, id_exe.nextPc, id_exe.readData1,
                    id_exe.readData2, aluOutput.aluCalcResult, id_exe.regDstResult, instEndPoint);

            //-----------------------------------Start MemoryAccess Stage------------------------------------

            //Memory Access
            MemoryOutput memoryOutput = memory.read(exe_mem.aluCalcResult, exe_mem.controlSignal, exe_mem.instEndPoint);

            memory.write(exe_mem.aluCalcResult, exe_mem.rtValue, exe_mem.controlSignal, exe_mem.instEndPoint);


            //MemToReg 위한 값 보내기
            memoryOutput.acceptAluResult(exe_mem.aluCalcResult, exe_mem.instEndPoint);
            memory.printExecutionMemoryAccess(exe_mem.controlSignal, exe_mem.aluCalcResult, exe_mem.rtValue, exe_mem.instEndPoint);


            //-----------------------------------Finish MemoryAccess Stage------------------------------------

            //Latch
            mem_wb.input(exe_mem.controlSignal, memoryOutput.memToRegResult, exe_mem.regDstValue, exe_mem.instEndPoint);


            //--------------------------------------Start WriteBack Stage------------------------------------

            //writeBack
            register.registerWrite(mem_wb.controlSignal, mem_wb.memToRegResult, mem_wb.regDst);
            register.printExecutionWriteBack(mem_wb.controlSignal, mem_wb.regDst);
            Logger.println();

            //--------------------------------------Finish WriteBack Stage------------------------------------

            //latch Update
            if_id.output();
            id_exe.output();
            exe_mem.output();
            mem_wb.output();

            //Valid값 갱신
            Global.IF_IDValid = Global.InputIF_IDValid;
            Global.ID_EXEValid = Global.InputID_EXEValid;
            Global.EXE_MEMValid = Global.InputEXE_MEMValid;
            Global.MEM_WBValid = Global.InputMEM_WBValid;

            //instEndpoint 값 갱신

            instEndPoint = inputInstEndPoint;
        }

        System.out.printf("\nresult value R[2] : %d\n", Global.register[2]);
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
            case 0 :
                returnValue =  basic;
                break;
            case 1 :
                returnValue = exe_memReturnVale;
                break;
            case 2 :
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
