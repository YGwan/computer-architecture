package com.CpuOutput;

import com.Cpu.ControlSignal;
import com.Logger;

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
        set();
    }


    //ALUSrc 처리 부분
    private void set() {
        aluSrcResult = mux(controlSignal.aluSrc, signExt, secondRegisterOutput);
        aluSrcResult = mux(controlSignal.ori, zeroExt, aluSrcResult);
    }

    public void printExecutionInput() {
        Logger.println("EX Input -> Input 1 : %d, Input 2 : %d\n", firstRegisterOutput, aluSrcResult);
    }
}
