package com.Latch;

import com.Cpu.ControlSignal;

public class EXE_MEM {

    public ControlSignal inputControlSignal;
    public int inputNextPc;
    public int inputRsValue;
    public int inputAluCalcResult;
    public int inputRtValue;
    public int inputRegDstValue;

    public ControlSignal controlSignal;
    public int nextPc;
    public int rsValue;
    public int aluCalcResult;
    public int rtValue;
    public int regDstValue;

    public void input(ControlSignal controlSignal, int nextPc, int rsValue,int rtValue,
                   int aluCalcResult, int regDstValue) {
        this.inputControlSignal = controlSignal;
        this.inputNextPc = nextPc;
        this.inputRsValue = rsValue;
        this.inputRtValue = rtValue;
        this.inputAluCalcResult = aluCalcResult;
        this.inputRegDstValue = regDstValue;

    }

    public void output(ControlSignal inputControlSignal, int inputNextPc, int inputRsValue, int inputRtValue,
                   int inputAluCalcResult, int inputRegDstValue) {
        this.controlSignal = inputControlSignal;
        this.nextPc = inputNextPc;
        this.rsValue = inputRsValue;
        this.rtValue = inputRtValue;
        this.aluCalcResult = inputAluCalcResult;
        this.regDstValue = inputRegDstValue;
    }
}
