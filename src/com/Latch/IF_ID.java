package com.Latch;


public class IF_ID {

    public boolean inputValid;
    private int inputIf_idPc;
    private String inputInstruction;
    public String inputHexInstruction;

    public boolean valid;
    public int if_idPc;
    public String instruction;
    public String hexInstruction;


    public void input(boolean valid, int if_idPc, String instruction, String hexInstruction) {
        this.inputValid = valid;
        this.inputIf_idPc = if_idPc;
        this.inputInstruction = instruction;
        this.inputHexInstruction = hexInstruction;
    }

    public void output() {
        this.valid = inputValid;
        this.if_idPc = inputIf_idPc;
        this.instruction = inputInstruction;
        this.hexInstruction = inputHexInstruction;
    }
}
