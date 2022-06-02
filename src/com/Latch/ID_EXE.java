package com.Latch;

import com.Cpu.ControlSignal;

public class ID_EXE {

    public boolean inputValid;
    private ControlSignal inputControlSignal;
    public int inputNextPc;
    public int inputReadData1;
    private int inputReadData2;
    private int inputSignExt;
    private int inputZeroExt;
    private int inputShamt;
    private int inputJumpAddr;
    private int inputBranchAddr;
    private int inputLoadUpper;
    private int inputRs;
    private int inputRt;
    private int inputRd;
    private boolean inputInstEndPoint;

    public boolean valid;
    public ControlSignal controlSignal;
    public int nextPc;
    public int readData1;
    public int readData2;
    public int signExt;
    public int zeroExt;
    public int shamt;
    public int jumpAddr;
    public int branchAddr;
    public int loadUpper;
    public int rs;
    public int rt;
    public int rd;
    public boolean instEndPoint;

    public void input(boolean valid, ControlSignal controlSignal, int nextPc, int readData1, int readData2, int signExt, int zeroExt,
                      int shamt, int jumpAddr, int branchAddr, int loadUpper, int rs, int rt, int rd, boolean instEndPoint) {
        this.inputValid = valid;
        this.inputControlSignal = controlSignal;
        this.inputNextPc = nextPc;
        this.inputReadData1 = readData1;
        this.inputReadData2 = readData2;
        this.inputSignExt = signExt;
        this.inputZeroExt = zeroExt;
        this.inputShamt = shamt;
        this.inputJumpAddr = jumpAddr;
        this.inputBranchAddr = branchAddr;
        this.inputLoadUpper = loadUpper;
        this.inputRs = rs;
        this.inputRt = rt;
        this.inputRd = rd;
        this.inputInstEndPoint = instEndPoint;
    }

    public void output() {
        this.valid = inputValid;
        this.controlSignal = inputControlSignal;
        this.nextPc = inputNextPc;
        this.readData1 = inputReadData1;
        this.readData2 = inputReadData2;;
        this.signExt = inputSignExt;
        this.zeroExt = inputZeroExt;
        this.shamt = inputShamt;
        this.jumpAddr = inputJumpAddr;
        this.branchAddr = inputBranchAddr;
        this.loadUpper = inputLoadUpper;
        this.rs = inputRs;
        this.rt = inputRt;
        this.rd = inputRd;
        this.instEndPoint = inputInstEndPoint;
    }


}
