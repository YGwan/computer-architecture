package com.Latch;

import com.Cpu.ControlSignal;

public class EXE_MEM {

    public boolean inputValid;
    private ControlSignal inputControlSignal;
    private int inputNextPc;
    public int inputFinalAluResult;
    public int inputRtValue;
    private int inputRegDstValue;
    private boolean inputInstEndPoint;

    public boolean valid;
    public ControlSignal controlSignal;
    public int nextPc;
    public int finalAluResult;
    public int rtValue;
    public int regDstValue;
    public boolean instEndPoint;

    public void input(boolean valid, ControlSignal controlSignal, int nextPc, int rtValue,
                   int finalAluResult, int regDstValue, boolean instEndPoint) {
        this.inputValid = valid;
        this.inputControlSignal = controlSignal;
        this.inputNextPc = nextPc;
        this.inputRtValue = rtValue;
        this.inputFinalAluResult = finalAluResult;
        this.inputRegDstValue = regDstValue;
        this.inputInstEndPoint = instEndPoint;
    }

    public void output() {
        this.valid = inputValid;
        this.controlSignal = inputControlSignal;
        this.nextPc = inputNextPc;
        this.rtValue = inputRtValue;
        this.finalAluResult = inputFinalAluResult;
        this.regDstValue = inputRegDstValue;
        this.instEndPoint = inputInstEndPoint;
    }

}
