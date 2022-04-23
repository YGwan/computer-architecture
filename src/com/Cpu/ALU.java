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

        //기본 연산 - (+)
        if (controlSignal.aluControl == 0) {
            aluResult = firstRegisterResult + aluSrcResult;
            return new AluOutput(aluResult);
        }

        //비교 연산 - slti
        else if (controlSignal.aluControl == 1) {
            if (firstRegisterResult < aluSrcResult) {
                return new AluOutput(1);
            } else return new AluOutput(0);
        }
        //비교 연산 - bne
        else if (controlSignal.aluControl == 2) {
            if (firstRegisterResult != aluSrcResult) {
                return new AluOutput(1);
            } else return new AluOutput(0);
        }
        //비교 연산 - beq
        else if (controlSignal.aluControl == 3) {
            if (firstRegisterResult == aluSrcResult) {
                return new AluOutput(1);
            } else return new AluOutput(0);
        }

        //사칙 연산 - subu(-)
        else if (controlSignal.aluControl == 4) {
            aluResult = firstRegisterResult - aluSrcResult;
            return new AluOutput(aluResult);
        }

        //논리 연산 - ori
        else if (controlSignal.aluControl == 5) {
            aluResult = firstRegisterResult | aluSrcResult;
            return new AluOutput(aluResult);
        }
        return new AluOutput(0);
    }


}
