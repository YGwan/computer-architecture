package com.Latch;


import com.Cpu.ControlSignal;

public class MEM_WB {

    private boolean inputValid;
    private int inputNextPc;
    private ControlSignal inputControlSignal;
    private int inputFinalAluResult;
    private int inputMemoryCalcResult;
    private int inputRegDst;
    private boolean inputInstEndPoint;

    public boolean valid;
    public int nextPc;
    public ControlSignal controlSignal;
    public int finalAluResult;
    public int memoryCalcResult;
    public int regDst;
    public boolean instEndPoint;

    public void input(boolean valid, int nextPc, ControlSignal controlSignal, int finalAluResult, int memoryCalcResult ,int regDst, boolean instEndPoint) {
        this.inputValid = valid;
        this.inputNextPc = nextPc;
        this.inputControlSignal = controlSignal;
        this.inputFinalAluResult = finalAluResult;
        this.inputMemoryCalcResult = memoryCalcResult;
        this.inputRegDst = regDst;
        this.inputInstEndPoint = instEndPoint;
    }

    public void output() {
        this.valid = inputValid;
        this.nextPc = inputNextPc;
        this.controlSignal = inputControlSignal;
        this.finalAluResult = inputFinalAluResult;
        this.memoryCalcResult = inputMemoryCalcResult;
        this.regDst = inputRegDst;
        this.instEndPoint = inputInstEndPoint;
    }


}
