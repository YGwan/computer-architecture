package com.CpuOutput;

import com.Cpu.ControlSignal;

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