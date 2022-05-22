package com;

import com.Memory.Global;

public class MemoryFetchOutput {

    String instruction;
    String hexInstruction;
    int nextPC;

    public MemoryFetchOutput(String instruction, String hexInstruction, int nextPC) {
        this.instruction = instruction;
        this.hexInstruction = hexInstruction;
        this.nextPC = nextPC;
        Global.nextPC = nextPC;
    }
}
