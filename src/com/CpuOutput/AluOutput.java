package com.CpuOutput;

import com.Logger;

/*
 *ALU에서 연산한 값 리턴
 * */
public class AluOutput {

    public int aluCalcResult;

    public AluOutput(int aluResult) {
        this.aluCalcResult = aluResult;
    }

    public void printExecutionOutput() {
        Logger.println("EX Stage -> result : %d\n", aluCalcResult);
    }

}
