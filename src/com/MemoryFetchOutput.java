package com;

import com.Memory.Global;

public class MemoryFetchOutput {

    String instruction;
    int nextPC;

    public MemoryFetchOutput(String instruction, int nextPC) {
        this.instruction = instruction;
        this.nextPC = nextPC;
        Global.pc = nextPC;
    }
}
