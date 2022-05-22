package com.Cpu;


import com.CpuOutput.AluOutput;
import com.Logger;
import com.Memory.Global;

public class ALU {

    ControlSignal controlSignal;

    int aluResult;


    //ALU 기본 연산
    public AluOutput process(int firstRegisterResult, int aluSrcResult, ControlSignal controlSignal) {

        this.controlSignal = controlSignal;

        if (Global.ID_EXEValid) {

            System.out.println("//////////////////");
            System.out.println(controlSignal.aluControl);
            System.out.println(aluResult);
            System.out.println(firstRegisterResult);
            System.out.println(aluSrcResult);
            System.out.println("//////////////////");

            //기본 연산 - (+)
            if (controlSignal.aluControl == 0) {
                System.out.println("///////");
                aluResult = firstRegisterResult + aluSrcResult;
                System.out.println(aluResult);
                System.out.println("결과~!~@!~@!~");
                return new AluOutput(controlSignal, aluResult);
            }

            //비교 연산 - slti
            else if (controlSignal.aluControl == 1) {
                if (firstRegisterResult < aluSrcResult) {
                    return new AluOutput(controlSignal, 1);
                } else return new AluOutput(controlSignal, 0);
            }
            //비교 연산 - bne
            else if (controlSignal.aluControl == 2) {
                if (firstRegisterResult != aluSrcResult) {
                    return new AluOutput(controlSignal, 1);
                } else return new AluOutput(controlSignal, 0);
            }
            //비교 연산 - beq
            else if (controlSignal.aluControl == 3) {
                if (firstRegisterResult == aluSrcResult) {
                    return new AluOutput(controlSignal, 1);
                } else return new AluOutput(controlSignal, 0);
            }

            //사칙 연산 - subu(-)
            else if (controlSignal.aluControl == 4) {
                aluResult = firstRegisterResult - aluSrcResult;
                return new AluOutput(controlSignal, aluResult);
            }

            //논리 연산 - ori
            else if (controlSignal.aluControl == 5) {
                aluResult = firstRegisterResult | aluSrcResult;
                return new AluOutput(controlSignal, aluResult);
            }

            //shift연산 - sll
            else if (controlSignal.aluControl == 6) {
                aluResult = aluSrcResult << firstRegisterResult;
                return new AluOutput(controlSignal, aluResult);
            }

        }
        return new AluOutput(null, 0);
    }
}
