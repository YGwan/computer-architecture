package com;

import com.Cpu.*;
import com.CpuOutput.AluOutput;
import com.CpuOutput.DecodeOutput;
import com.CpuOutput.MemoryOutput;
import com.CpuOutput.RegisterOutput;
import com.Latch.ID_EXE;
import com.Latch.IF_ID;
import com.Memory.Global;

import java.io.IOException;

public class Main extends Global {

    public static void main(String[] args) throws IOException {

        Logger.LOGGING_SIGNAL = true;
        Logger.LOGGING_COUNTER_SIGNAL = true;

        initializedRegister();

        String path = "source/simple.bin";

        Logger.println("\n----------pipeline Start----------\n");

        //Fetch 선언 및 Instruction Fetch
        MemoryFetch memoryFetch = new MemoryFetch(path);
//        memoryFetch.printBitInstruction();
//        memoryFetch.printhexInstruction();

        //객체 생성 - 하드웨어 컴포넌트 생성
        ControlSignal controlSignal = new ControlSignal(); //Decode 함수에서 초기화 작업 진행
        Decode decode = new Decode(controlSignal);
        Register register = new Register(controlSignal);
        PC pcUpdate = new PC(controlSignal);
        ALU alu = new ALU(controlSignal);
        Memory memory = new Memory(controlSignal);

        //Latch 선언
        IF_ID if_id = new IF_ID();
        ID_EXE id_exe = new ID_EXE();

        int cycleCount = 1;

        while (pc != -1) {
            //total cycle 수 측정
            if(cycleCount % 1000000 == 0) {
                Logger.countPrintln("Cycle Count : %d\n", cycleCount++);
            }

            // instruction fetch
            MemoryFetchOutput memoryFetchOutput = memoryFetch.fetch(pc);
            String pcHex = Integer.toHexString(pc * 4);

            //Latch
            if_id.input(memoryFetchOutput.nextPC, memoryFetchOutput.instruction, memoryFetchOutput.hexInstruction);
            Logger.println("cyl %d, IF Stage -> pc : 0x%s, instruction : 0x%s\n", cycleCount++, pcHex, if_id.inputHexInstruction);


            //------------------------------------Start Decode Stage------------------------------------
            // instruction decode
            DecodeOutput decodeOutput = decode.decodeInstruction(if_id.instruction);
            decodeOutput.printDecodeStage();
            Global.IF_IDValid = false;

            RegisterOutput registerOutput = register.registerCalc(decodeOutput.rs,
                    decodeOutput.rt, decodeOutput.regDstResult);

            //ALUSrc를 위해 signExt ,zeroExt, shamt보내기
            registerOutput.acceptSignExt(decodeOutput.signExt);
            registerOutput.acceptZeroExt(decodeOutput.zeroExt);
            registerOutput.acceptShamt(decodeOutput.shamt);
            registerOutput.printExecutionInput();

            //------------------------------------Finish Decode Stage------------------------------------

            //Latch
            id_exe.input(controlSignal, nextPC, registerOutput.firstRegisterOutput,
                    registerOutput.aluSrcResult, decodeOutput.rs, decodeOutput.rd);
            Global.ID_EXEValid = false;


            //Execution
            AluOutput aluOutput = alu.process(registerOutput.firstValue, registerOutput.aluSrcResult);
            aluOutput.acceptLoadUpperImm(decodeOutput.loadUpperImm);
            aluOutput.printExecutionOutput();

            //Memory Access
            MemoryOutput memoryOutput = memory.read(aluOutput.aluCalcResult);
            memory.write(aluOutput.aluCalcResult, registerOutput.secondRegisterOutput);

            //MemtoReg를 위한 값 보내기
            memoryOutput.acceptAluResult(aluOutput.aluCalcResult);
            memory.printExecutionMemoryAccess();

            //writeBack
            register.registerWrite(memoryOutput.memToRegResult);
            register.printExecutionWriteBack();

//            //pc Update
//            pcUpdate.setInstruction(memoryFetchOutput.instruction);
//            pcUpdate.pcUpdate(registerOutput.firstRegisterOutput, aluOutput.aluCalcResult);
//            Logger.println();

            //latch Update
            if_id.output(if_id.inputNextPc, if_id.inputInstruction, if_id.inputHexInstruction);
            Global.IF_IDValid = true;
            id_exe.output(id_exe.inputControlSignal, id_exe.inputNextPc, id_exe.inputReadData1,
                    id_exe.inputAluSrcResult, id_exe.inputRs, id_exe.inputRd);
            Global.ID_EXEValid = true;


            //임시 pc update
            pc = mux(controlSignal.jr, registerOutput.firstRegisterOutput, pc);
            Logger.println();
        }
        System.out.printf("\nresult value R[2] : %d\n", Global.register[2]);
     }

    public static  int mux(boolean signal, int trueVal, int falseVal) {
        if (signal) {
            return trueVal;
        }
        return falseVal;
    }

    private static void initializedRegister() {
        pc = 0;
        register[29] = 0x1000000;
        register[31] = 0xFFFFFFFF;
    }

}
