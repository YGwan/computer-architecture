package com.CpuOutput;

import com.Memory.Global;

public class MemoryFetchOutput {

    public String  instruction;
    public String hexInstruction;
    public int nextPC;

    public MemoryFetchOutput(String instruction, String hexInstruction, int nextPC) {
        this.instruction = instruction;
        this.hexInstruction = hexInstruction;
        this.nextPC = nextPC;
        Global.nextPC = nextPC;
    }
}
