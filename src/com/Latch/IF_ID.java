package com.Latch;


import com.Memory.Global;

public class IF_ID {


    private int inputNextPc;
    private String inputInstruction;
    public String inputHexInstruction;

    public int nextPC;
    public String instruction;
    public String hexInstruction;


    public void input(int nextPC, String instruction, String hexInstruction) {
        this.inputNextPc = nextPC;
        this.inputInstruction = instruction;
        this.inputHexInstruction = hexInstruction;
    }

    public void output() {
        this.nextPC = inputNextPc;
        this.instruction = inputInstruction;
        this.hexInstruction = inputHexInstruction;
    }

//    public void flush() {
//
//        Global.
//    }
}
