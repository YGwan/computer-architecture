package com.CpuOutput;

import com.Cpu.ControlSignal;
import com.Memory.Global;

import javax.xml.bind.annotation.XmlElementDecl;

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

    public int aluSrcResult;

    public RegisterOutput(ControlSignal controlSignal, int firstRegisterOutput, int secondRegisterOutput) {
        this.controlSignal = controlSignal;
        this.firstRegisterOutput = firstRegisterOutput;
        this.secondRegisterOutput = secondRegisterOutput;
    }

    public void acceptSignExt(int signExt) {
        this.signExt = signExt;
        set();
    }

    //ALUSrc 처리 부분
    private void set() {
        aluSrcResult = mux(controlSignal.aluSrc, signExt, secondRegisterOutput);
    }

    public void executionInputPrint() {
        System.out.printf("EX Input -> ALU Input1 : %d, ALU Input2 : %d\n", firstRegisterOutput, aluSrcResult);
    }
}
