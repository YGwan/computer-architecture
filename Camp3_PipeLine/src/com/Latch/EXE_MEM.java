package com.Latch;

import com.Cpu.ControlSignal;

public class EXE_MEM {

    public boolean inputValid;
    private ControlSignal inputControlSignal;
    private int inputExe_memPc;
    public int inputFinalAluResult;
    public int inputRtValue;
    private int inputRegDstValue;
    private boolean inputInstEndPoint;

    public boolean valid;
    public ControlSignal controlSignal;
    public int exe_memPc;
    public int finalAluResult;
    public int rtValue;
    public int regDstValue;
    public boolean instEndPoint;

    public void input(boolean valid, ControlSignal controlSignal, int exe_memPc, int rtValue,
                   int finalAluResult, int regDstValue, boolean instEndPoint) {
        this.inputValid = valid;
        this.inputControlSignal = controlSignal;
        this.inputExe_memPc = exe_memPc;
        this.inputRtValue = rtValue;
        this.inputFinalAluResult = finalAluResult;
        this.inputRegDstValue = regDstValue;
        this.inputInstEndPoint = instEndPoint;
    }

    public void output() {
        this.valid = inputValid;
        this.controlSignal = inputControlSignal;
        this.exe_memPc = inputExe_memPc;
        this.rtValue = inputRtValue;
        this.finalAluResult = inputFinalAluResult;
        this.regDstValue = inputRegDstValue;
        this.instEndPoint = inputInstEndPoint;
    }

}
