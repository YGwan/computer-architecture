package com.Cpu;


import com.CpuOutput.AluOutput;
import com.Memory.Global;

import static com.Main.mux;

public class ALU {


    int aluResult;

    //ALUSrc, sll 처리 부분

    public int setAluInput1(boolean ID_EXEValid,ControlSignal controlSignal, int shamt, int readData1) {
        if(ID_EXEValid) {
            return mux(controlSignal.sll, shamt, readData1);
        } return 0;
    }

    public int setAluInput2(boolean ID_EXEValid,ControlSignal controlSignal, int signExt, int zeroExt, int readData2) {
        if(ID_EXEValid) {
            int aluInput2 = mux(controlSignal.aluSrc, signExt, readData2);
            return mux(controlSignal.ori, zeroExt, aluInput2);
        } return 0;
    }



    //ALU 기본 연산
    public AluOutput process(boolean ID_EXEValid,ControlSignal controlSignal, int firstRegisterResult, int aluSrcResult) {

        if (ID_EXEValid) {

            //기본 연산 - (+)
            if (controlSignal.aluControl == 0) {
                aluResult = firstRegisterResult + aluSrcResult;
                return new AluOutput(controlSignal, aluResult);
            }

            //비교 연산 - slti
            else if (controlSignal.aluControl == 1) {

                if (firstRegisterResult < aluSrcResult) {
                    return new AluOutput(controlSignal, 1);
                } else {
                    return new AluOutput(controlSignal, 0);
                }
            }
            //비교 연산 - bne
            else if (controlSignal.aluControl == 2) {
                if (firstRegisterResult != aluSrcResult) {
                    return new AluOutput(controlSignal, 1);
                } else {
                    return new AluOutput(controlSignal, 0);
                }
            }
            //비교 연산 - beq
            else if (controlSignal.aluControl == 3) {
                if (firstRegisterResult == aluSrcResult) {
                    return new AluOutput(controlSignal, 1);
                } else {
                    return new AluOutput(controlSignal, 0);
                }
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
