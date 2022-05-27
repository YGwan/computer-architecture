package com.Latch;


import com.Cpu.ControlSignal;

public class MEM_WB {

    private ControlSignal inputControlSignal;
    private int inputFinalAluResult;
    private int inputMemoryCalcResult;
    private int inputRegDst;
    private boolean inputInstEndPoint;


    public ControlSignal controlSignal;
    public int finalAluResult;
    public int memoryCalcResult;
    public int regDst;
    public boolean instEndPoint;

    public void input(ControlSignal controlSignal, int finalAluResult, int memoryCalcResult ,int regDst, boolean instEndPoint) {
        this.inputControlSignal = controlSignal;
        this.inputFinalAluResult = finalAluResult;
        this.inputMemoryCalcResult = memoryCalcResult;
        this.inputRegDst = regDst;
        this.inputInstEndPoint = instEndPoint;
    }

    public void output() {
        this.controlSignal = inputControlSignal;
        this.finalAluResult = inputFinalAluResult;
        this.memoryCalcResult = inputMemoryCalcResult;
        this.regDst = inputRegDst;
        this.instEndPoint = inputInstEndPoint;
    }


}
