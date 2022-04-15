package com;

import com.Cpu.Register;
import com.Cpu.FetchInst;
import com.Memory.Global;

import java.io.IOException;
import java.util.List;

public class Main extends Global {

    public static void main(String[] args) throws IOException {

        initializedRegister();
        register[29] += 1;

        String path = "source/simple.bin";
        FetchInst fetchInst = new FetchInst(path);
        //Instruction Fetch
        List<String> binaryInstructions = fetchInst.fetch();
//        fetchMachine.printBitInstruction();
//        fetchMachine.printhexInstruction();

        for(int bitIndex=0; bitIndex<binaryInstructions.size(); bitIndex++) {
            Register register = new Register(binaryInstructions.get(bitIndex));//bitIndex
            register.decodeInstruction();
        }
        System.out.println();
        System.out.println(register[30]);


    }

    private static void initializedRegister() {
        pc = 0;
        register[29] = 0xFFFFFFFF;
        register[31] = 0x10000000;
    }
}
