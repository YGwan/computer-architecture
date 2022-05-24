package com.Cpu;

import com.CpuOutput.MemoryOutput;
import com.Logger;
import com.Memory.Global;

public class Memory {

    private final int memesize = 0x1000000;
    public int[] dataMemory = new int[memesize];

    //MemRead
    public MemoryOutput read(int address, ControlSignal controlSignal) {

        if (Global.EXE_MEMValid) {

            System.out.println("////////////////");
            System.out.println(controlSignal.inst);
            System.out.println("////////////////");

            if (controlSignal.memRead) {
                Global.InputMEM_WBValid = true;
                return new MemoryOutput(controlSignal, dataMemory[address]);
            } else {
                Global.InputMEM_WBValid = true;
                return new MemoryOutput(controlSignal, 0);
            }
        } else {
            return new MemoryOutput(null, 0);
        }
    }

    //MemWrite
    public void write(int address, int writedata, ControlSignal controlSignal) {
        if (Global.EXE_MEMValid) {
            if (controlSignal.memWrite) {
                dataMemory[address] = writedata;
            }
        }
    }

    public void printExecutionMemoryAccess(ControlSignal controlSignal, int address, int writeData) {
        if (Global.EXE_MEMValid) {
            String index = Integer.toHexString(address);
            if (controlSignal.memRead) {
                Logger.println("MA Stage -> M[0x%s] = %d\n", index, dataMemory[address]);

            } else if (controlSignal.memWrite) {
                Logger.println("MA Stage -> M[0x%s] = %d\n", index, writeData);
            } else {
                Logger.println("MA Stage -> ");
            }
        } else {
            Logger.println("MA Stage -> [NOP]");
        }
    }
}
