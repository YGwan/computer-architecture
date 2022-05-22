package com.CpuOutput;

import com.Cpu.ControlSignal;
import com.Logger;
import com.Memory.Global;

import static com.Main.mux;

/*
 * ALU로 들어가는 값 2개
 * ALUSrc 처리
 * */
public class RegisterOutput {

    ControlSignal controlSignal;
    public int firstRegisterOutput;
    public int secondRegisterOutput;
    private int signExt;
    private int zeroExt;
    private int shamt;

    public int firstValue;
    public int aluSrcResult;

    public RegisterOutput(ControlSignal controlSignal, int firstRegisterOutput, int secondRegisterOutput) {
        this.controlSignal = controlSignal;
        this.firstRegisterOutput = firstRegisterOutput;
        this.secondRegisterOutput = secondRegisterOutput;
    }

    public void acceptSignExt(int signExt) {
        this.signExt = signExt;
    }

    public void acceptZeroExt(int zeroExt) {
        this.zeroExt = zeroExt;
    }

    //shamt 처리 부분
    public void acceptShamt(int shamt) {
        this.shamt = shamt;
        set();
    }


    //ALUSrc, sll 처리 부분
    private void set() {
        aluSrcResult = mux(controlSignal.aluSrc, signExt, secondRegisterOutput);
        aluSrcResult = mux(controlSignal.ori, zeroExt, aluSrcResult);
        firstValue = mux(controlSignal.sll, shamt, firstRegisterOutput);
    }

}
