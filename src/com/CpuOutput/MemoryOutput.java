package com.CpuOutput;

import com.Cpu.ControlSignal;
import com.Memory.Global;

import static com.Main.mux;

/*
 * memory에서 연산한 값 리턴
 * */
public class MemoryOutput {

    ControlSignal controlSignal;
    public int memoryCalcResult;
    private int aluResult;

    public int memToRegResult;

    public MemoryOutput(ControlSignal controlSignal, int memoryCalcResult) {
        this.controlSignal = controlSignal;
        this.memoryCalcResult = memoryCalcResult;
    }

    public void acceptAluResult(int aluResult, boolean instEndPoint) {

        if(!instEndPoint) {
            if (Global.EXE_MEMValid) {
                this.aluResult = aluResult;
                set();
            }
        }
    }

    //MemtoReg 처리 부분
    private void set() {
        if (Global.EXE_MEMValid) {
            memToRegResult = mux(controlSignal.memToReg, memoryCalcResult, aluResult);
        }
    }
}
