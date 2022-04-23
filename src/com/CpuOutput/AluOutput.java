package com.CpuOutput;

/*
*ALU에서 연산한 값 리턴
* */
public class AluOutput {

    public int aluCalcResult;

    public AluOutput(int aluResult) {
        this.aluCalcResult = aluResult;
    }

    public void executionOutputPrint() {
        System.out.printf("EX Stage -> result : %d\n", aluCalcResult);
    }

}
