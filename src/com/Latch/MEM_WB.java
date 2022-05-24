package com.Latch;


import com.Cpu.ControlSignal;

public class MEM_WB {

    private ControlSignal inputControlSignal;
    private int inputMemToRegResult;
    private int inputRegDst;
    private boolean inputInstEndPoint;

    public ControlSignal controlSignal;
    public int memToRegResult;
    public int regDst;
    public boolean instEndPoint;

    public void input(ControlSignal controlSignal, int memToRegResult, int regDst, boolean instEndPoint) {
        this.inputControlSignal = controlSignal;
        this.inputMemToRegResult = memToRegResult;
        this.inputRegDst = regDst;
        this.inputInstEndPoint = instEndPoint;
    }

    public void output() {
        this.controlSignal = inputControlSignal;
        this.memToRegResult = inputMemToRegResult;
        this.regDst = inputRegDst;
        this.instEndPoint = inputInstEndPoint;
    }


}
