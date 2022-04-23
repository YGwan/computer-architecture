package com.Cpu;

import com.CpuOutput.MemoryOutput;
import com.Logger;

public class Memory {

    private final int memesize = 0x1000000;
    public int[] dataMemory = new int[memesize];

    ControlSignal controlSignal;

    private int address;
    private int writedata;

    public Memory(ControlSignal controlSignal) {
        this.controlSignal = controlSignal;
    }

    //MemtoReg = true
    public MemoryOutput read(int address) {
        if (controlSignal.memRead) {
            this.address = address;
            return new MemoryOutput(controlSignal, dataMemory[address]);
        }
        return new MemoryOutput(controlSignal, 0);
    }

    //MemtoWrite
    public void write(int address, int writedata) {
        this.address = address;
        this.writedata = writedata;
        if (controlSignal.memWrite) {
            dataMemory[address] = writedata;
        }
    }

    public void printExecutionMemoryAccess() {
        String index = Integer.toHexString(address);
        if (controlSignal.memRead) {
            Logger.println("MA Stage -> M[0x%s] = %d\n", index, dataMemory[address]);
        } else if (controlSignal.memWrite) {
            Logger.println("MA Stage -> M[0x%s] = %d\n", index, writedata);
        } else {
            Logger.println("MA Stage -> ");
        }
    }
}
