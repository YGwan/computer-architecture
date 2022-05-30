package com.CpuOutput;

import com.Logger;
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

    public void printFetchStage(boolean fetchValid, int cycleCount, String pcHex, String instruction) {
        if (fetchValid || instruction != null) {
            Logger.println("cyl %d, IF Stage -> pc : 0x%s, instruction : 0x%s\n", cycleCount, pcHex, instruction);
        } else {
            Logger.println("cyl %d, IF Stage -> [NOP]\n", cycleCount);
        }


    }
}
