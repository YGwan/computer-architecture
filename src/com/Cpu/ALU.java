package com.Cpu;


import com.CpuOutput.AluOutput;

public class ALU {

    ControlSignal controlSignal;

    int aluResult;

    public ALU(ControlSignal controlSignal) {
        this.controlSignal = controlSignal;
    }


    //ALU 기본 연산
    public AluOutput process(int firstRegisterResult, int aluSrcResult) {
        aluResult = firstRegisterResult + aluSrcResult;
        return new AluOutput(aluResult);
    }

}
