package com.Latch;


public class IF_ID {

    public int nextPC;
    public String instruction;
    public String hexInstruction;

    public int inputNextPc;
    public String inputInstruction;
    public String inputHexInstruction;

    public void input(int nextPC, String instruction, String hexInstruction) {
        this.inputNextPc = nextPC;
        this.inputInstruction = instruction;
        this.inputHexInstruction = hexInstruction;
    }

    public void output(int inputNextPc, String inputInstruction, String inputHexInstruction) {
        this.nextPC = inputNextPc;
        this.instruction = inputInstruction;
        this.hexInstruction = inputHexInstruction;
    }
}
