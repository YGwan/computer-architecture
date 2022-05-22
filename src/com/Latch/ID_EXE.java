package com.Latch;

import com.Cpu.ControlSignal;

public class ID_EXE {

    public ControlSignal inputControlSignal;
    public int inputNextPc;
    public int inputReadData1;
    public int inputAluSrcResult;
    public int inputRs;
    public int inputRd;

    public ControlSignal controlSignal;
    public int nextPc;
    public int readData1;
    public int aluSrcResult;
    public int rs;
    public int rd;

    public void input(ControlSignal controlSignal, int nextPc, int readData1, int AluSrcResult, int rs, int rd) {
        this.inputControlSignal = controlSignal;
        this.inputNextPc = nextPc;
        this.inputReadData1 = readData1;
        this.inputAluSrcResult = AluSrcResult;
        this.inputRs = rs;
        this.inputRd = rd;
    }

    public void output(ControlSignal inputControlSignal, int inputNextPc, int inputReadData1,
                       int inputAluSrcResult, int inputRs, int inputRd) {
        this.controlSignal = inputControlSignal;
        this.nextPc = inputNextPc;
        this.readData1 = inputReadData1;
        this.aluSrcResult = inputAluSrcResult;
        this.rs = inputRs;
        this.rd = inputRd;
    }


}
