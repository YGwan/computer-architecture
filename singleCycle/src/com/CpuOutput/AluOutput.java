package com.CpuOutput;

import com.Cpu.ControlSignal;
import com.Logger;

import static com.Main.mux;

/*
 *ALU에서 연산한 값 리턴
 */
public class AluOutput {

    ControlSignal controlSignal;
    public int aluCalcResult;
    private int loadUpperImm;
    private int aluResult;

    public AluOutput(ControlSignal controlSignal, int aluResult) {
        this.controlSignal = controlSignal;
        this.aluResult = aluResult;
    }



    public void acceptLoadUpperImm(int loadUpperImm) {
        this.loadUpperImm = loadUpperImm;
        set();
    }

    private void set() {
        aluCalcResult = mux(controlSignal.lui, loadUpperImm, aluResult);
    }

    public void printExecutionOutput() {
        Logger.println("EX Stage -> result : %d\n", aluCalcResult);
    }

}
