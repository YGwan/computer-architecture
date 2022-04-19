package com;

import com.Cpu.*;
import com.Memory.Global;

import java.io.IOException;

public class Main extends Global {

    public static void main(String[] args) throws IOException {

        initializedRegister();

        String path = "source/simple.bin";

        //Fetch 선언 및 Instruction Fetch
        MemoryFetch memoryFetch = new MemoryFetch(path);
//        memoryFetch.printBitInstruction();
//        memoryFetch.printhexInstruction();

        //객체 생성 - 하드웨어 컴포넌트 생성
        ControlSignal controlSignal = new ControlSignal(); //Decode 함수에서 초기화 작업 진행
        Decode decode = new Decode(controlSignal);
        Register register = new Register(controlSignal);
        ALU alu = new ALU(controlSignal);
        Memory memory = new Memory(controlSignal);

        while (pc < memoryFetch.size()) { //나중에 -1 조건으로 고치기

            // instruction fetch
            String inst = memoryFetch.fetch(pc);

            //pc update
            pc++;

            // instruction decode
            DecodeOutput decodeOutput = decode.decodeInstruction(inst);
            RegisterOutput registerOutput = register.registerCalc(decodeOutput.opcode, decodeOutput.rs,
                    decodeOutput.rt, decodeOutput.regDstResult, decodeOutput.func);
            //ALUSrc를 위해 signExt 보내기
            registerOutput.acceptSignExt(decodeOutput.signExt);

            //Execution
            AluOutput aluOutput = alu.process(registerOutput.firstRegisterOutput, registerOutput.aluSrcResult);

            //Memory Access
            MemoryOutput memoryOutput = memory.read(aluOutput.aluCalcResult);
            memory.write(aluOutput.aluCalcResult, registerOutput.secondRegisterOutput);
           //MemtoReg를 위한 값 보내기
            memoryOutput.acceptAluResult(aluOutput.aluCalcResult);
            //writeBack
            register.registerWrite(decodeOutput.regDstResult,memoryOutput.memToRegResult);
        }


    }

    public static int mux(boolean signal, int trueVal, int falseVal) {
        if(signal) {
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
