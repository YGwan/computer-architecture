package com.Cpu;


import com.CpuOutput.AluOutput;
import com.Logger;

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
            return new AluOutput(controlSignal, aluResult);
        }

        //비교 연산 - slti
        else if (controlSignal.aluControl == 1) {
            if (firstRegisterResult < aluSrcResult) {
                return new AluOutput(controlSignal,1);
            } else return new AluOutput(controlSignal,0);
        }
        //비교 연산 - bne
        else if (controlSignal.aluControl == 2) {
            if (firstRegisterResult != aluSrcResult) {
                return new AluOutput(controlSignal,1);
            } else return new AluOutput(controlSignal,0);
        }
        //비교 연산 - beq
        else if (controlSignal.aluControl == 3) {
            if (firstRegisterResult == aluSrcResult) {
                return new AluOutput(controlSignal,1);
            } else return new AluOutput(controlSignal,0);
        }

        //사칙 연산 - subu(-)
        else if (controlSignal.aluControl == 4) {
            aluResult = firstRegisterResult - aluSrcResult;
            return new AluOutput(controlSignal,aluResult);
        }

        //논리 연산 - ori
        else if (controlSignal.aluControl == 5) {
            aluResult = firstRegisterResult | aluSrcResult;
            return new AluOutput(controlSignal,aluResult);
        }

        //shift연산 - sll
        else if(controlSignal.aluControl == 6) {
            Logger.println("!!!!!" + firstRegisterResult);
            aluResult = aluSrcResult << firstRegisterResult;
            return new AluOutput(controlSignal,aluResult);
        }

        return new AluOutput(controlSignal,0);
    }


}
