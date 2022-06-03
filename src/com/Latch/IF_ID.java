package com.Latch;


public class IF_ID {

    public boolean inputValid;
    private int inputNextPc;
    private String inputInstruction;
    public String inputHexInstruction;

    public boolean valid;
    public int nextPc;
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
        this.nextPc = inputNextPc;
        this.instruction = inputInstruction;
        this.hexInstruction = inputHexInstruction;
    }
}
