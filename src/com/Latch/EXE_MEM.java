package com.Latch;

import com.Cpu.ControlSignal;

public class EXE_MEM {

    private ControlSignal inputControlSignal;
    private int inputNextPc;
    private int inputRsValue;
    public int inputAluCalcResult;
    private int inputRtValue;
    private int inputRegDstValue;
    private boolean inputInstEndPoint;

    public ControlSignal controlSignal;
    public int nextPc;
    public int rsValue;
    public int aluCalcResult;
    public int rtValue;
    public int regDstValue;
    public boolean instEndPoint;

    public void input(ControlSignal controlSignal, int nextPc, int rsValue,int rtValue,
                   int aluCalcResult, int regDstValue, boolean instEndPoint) {
        this.inputControlSignal = controlSignal;
        this.inputNextPc = nextPc;
        this.inputRsValue = rsValue;
        this.inputRtValue = rtValue;
        this.inputAluCalcResult = aluCalcResult;
        this.inputRegDstValue = regDstValue;
        this.inputInstEndPoint = instEndPoint;
    }

    public void output() {
        this.controlSignal = inputControlSignal;
        this.nextPc = inputNextPc;
        this.rsValue = inputRsValue;
        this.rtValue = inputRtValue;
        this.aluCalcResult = inputAluCalcResult;
        this.regDstValue = inputRegDstValue;
        this.instEndPoint = inputInstEndPoint;
    }
}
