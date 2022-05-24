package com.Latch;

import com.Cpu.ControlSignal;

public class ID_EXE {

    public ControlSignal inputControlSignal;
    private int inputNextPc;
    private int inputReadData1;
    private int inputReadData2;
    private int inputAluSrcResult;
    private int inputRegDstResult;

    public ControlSignal controlSignal;
    public int nextPc;
    public int readData1;
    public int readData2;
    public int aluSrcResult;
    public int regDstResult;

    public void input(ControlSignal controlSignal, int nextPc, int readData1, int readData2, int AluSrcResult, int RegDstResult) {
        this.inputControlSignal = controlSignal;
        this.inputNextPc = nextPc;
        this.inputReadData1 = readData1;
        this.inputReadData2 = readData2;
        this.inputAluSrcResult = AluSrcResult;
        this.inputRegDstResult = RegDstResult;

    }

    public void output() {
        this.controlSignal = inputControlSignal;
        this.nextPc = inputNextPc;
        this.readData1 = inputReadData1;
        this.readData2 = inputReadData2;
        this.aluSrcResult = inputAluSrcResult;
        this.regDstResult = inputRegDstResult;
    }


}
