package com.Latch;


import com.Cpu.ControlSignal;

public class MEM_WB {

    private ControlSignal inputControlSignal;
    private int inputMemToRegResult;
    private int inputRegDst;

    public ControlSignal controlSignal;
    public int memToRegResult;
    public int regDst;

    public void input(ControlSignal controlSignal, int memToRegResult, int regDst) {
        this.inputControlSignal = controlSignal;
        this.inputMemToRegResult = memToRegResult;
        this.inputRegDst = regDst;
    }

    public void output() {
        this.controlSignal = inputControlSignal;
        this.memToRegResult = inputMemToRegResult;
        this.regDst = inputRegDst;
    }


}
