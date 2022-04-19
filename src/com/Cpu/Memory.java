package com.Cpu;

public class Memory {

    private int memesize = 0x1000000;
    public int[] dataMemory = new int[memesize];

    ControlSignal controlSignal;
    int address;

    public Memory(ControlSignal controlSignal) {
        this.controlSignal = controlSignal;
    }

    //MemtoReg = true
    public MemoryOutput read(int address) {
        if (controlSignal.memRead) {
            return new MemoryOutput(controlSignal, dataMemory[address / 4]);
        }
        return new MemoryOutput(controlSignal, 0);
    }

    //MemtoWrite
    public void write(int address, int writedata) {
        this.address = address;
        if (controlSignal.memWrite) {
            dataMemory[address / 4] = writedata;
        }
    }

    public void printDataMemory() {
        System.out.println(dataMemory[address/4]);
    }
}
