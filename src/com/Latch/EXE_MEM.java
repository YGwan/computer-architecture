package com.Latch;

import com.Cpu.ControlSignal;

public class EXE_MEM {

    private ControlSignal inputControlSignal;
    private int inputNextPc;
    public int inputAluResult;
    private int inputRtValue;
    private int inputRegDstValue;
    private boolean inputInstEndPoint;

    public ControlSignal controlSignal;
    public int nextPc;
    public int aluResult;
    public int rtValue;
    public int regDstValue;
    public boolean instEndPoint;

    public void input(ControlSignal controlSignal, int nextPc, int rtValue,
                   int aluResult, int regDstValue, boolean instEndPoint) {
        this.inputControlSignal = controlSignal;
        this.inputNextPc = nextPc;
        this.inputRtValue = rtValue;
        this.inputAluResult = aluResult;
        this.inputRegDstValue = regDstValue;
        this.inputInstEndPoint = instEndPoint;
    }

    public void output() {
        this.controlSignal = inputControlSignal;
        this.nextPc = inputNextPc;
        this.rtValue = inputRtValue;
        this.aluResult = inputAluResult;
        this.regDstValue = inputRegDstValue;
        this.instEndPoint = inputInstEndPoint;
    }
}
