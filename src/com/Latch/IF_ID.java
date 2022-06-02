package com.Latch;


import com.Memory.Global;

public class IF_ID {

    public boolean inputValid;
    private int inputNextPc;
    private String inputInstruction;
    public String inputHexInstruction;

    public boolean valid;
    public int nextPC;
    public String instruction;
    public String hexInstruction;


    public void input(boolean valid, int nextPC, String instruction, String hexInstruction) {
        this.inputValid = valid;
        this.inputNextPc = nextPC;
        this.inputInstruction = instruction;
        this.inputHexInstruction = hexInstruction;
    }

    public void output() {
        this.valid = inputValid;
        this.nextPC = inputNextPc;
        this.instruction = inputInstruction;
        this.hexInstruction = inputHexInstruction;
    }
}
