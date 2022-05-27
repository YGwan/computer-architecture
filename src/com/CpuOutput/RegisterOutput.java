package com.CpuOutput;

import com.Cpu.ControlSignal;

/*
 * ALU로 들어가는 값 2개
 * ALUSrc 처리
 * */
public class RegisterOutput {

    ControlSignal controlSignal;
    public int firstRegisterOutput;
    public int secondRegisterOutput;

    public RegisterOutput(ControlSignal controlSignal, int firstRegisterOutput, int secondRegisterOutput) {
        this.controlSignal = controlSignal;
        this.firstRegisterOutput = firstRegisterOutput;
        this.secondRegisterOutput = secondRegisterOutput;
    }
}