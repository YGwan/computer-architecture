package com;

import com.Cpu.*;
import com.CpuOutput.AluOutput;
import com.CpuOutput.DecodeOutput;
import com.CpuOutput.MemoryOutput;
import com.CpuOutput.RegisterOutput;
import com.Memory.Global;

import java.io.IOException;

public class Main extends Global {

    public static void main(String[] args) throws IOException {

        Logger.LOGGING_SIGNAL = false;
        Logger.LOGGING_COUNTER_SIGNAL = true;

        initializedRegister();

        String path = "source/input4.bin";

        Logger.println("\n----------Cycle Start----------\n");

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

        int cycleCount = 1;

        while (pc != -1) {

//            try {
//                Thread.sleep(30);
//            } catch ( Exception e) {
//
//            }

            if(cycleCount > 26000) {
                Logger.LOGGING_SIGNAL = false;
            }

            if(cycleCount > 25000 && cycleCount < 26000) {
                Logger.LOGGING_SIGNAL = true;
            }

            //total cycle 수 측정
            if(cycleCount % 1000000 == 0) {
                Logger.countPrintln("Cycle Count : %d\n", cycleCount++);
            }
            // instruction fetch
            String inst = memoryFetch.fetch(pc);
            String pcHex = Integer.toHexString(pc * 4);
            Logger.println("cyl %d, IF Stage -> pc : 0x%s, instruction : 0x%s\n", cycleCount++, pcHex, memoryFetch.printHexInst(pc));



            // instruction decode
            DecodeOutput decodeOutput = decode.decodeInstruction(inst);
            decodeOutput.printDecodeStage();

            RegisterOutput registerOutput = register.registerCalc(decodeOutput.rs,
                    decodeOutput.rt, decodeOutput.regDstResult);


            //ALUSrc를 위해 signExt ,zeroExt, shamt보내기
            registerOutput.acceptSignExt(decodeOutput.signExt);
            registerOutput.acceptZeroExt(decodeOutput.zeroExt);
            registerOutput.acceptShamt(decodeOutput.shamt);
            registerOutput.printExecutionInput();

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

            //pc Update
            pcUpdate.setInstruction(inst);
            pcUpdate.pcUpdate(registerOutput.firstRegisterOutput, aluOutput.aluCalcResult);
            Logger.println("");
        }
        System.out.println("###################");
        System.out.println(Global.register[2]);

    }

    public static int mux(boolean signal, int trueVal, int falseVal) {
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
