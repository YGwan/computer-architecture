package com.CpuOutput;

import com.Cpu.ControlSignal;
import com.Logger;
import com.Memory.Global;

public class AluOutput {

    ControlSignal controlSignal;
    public int aluResult;

    public AluOutput(ControlSignal controlSignal, int aluResult) {
        this.controlSignal = controlSignal;
        this.aluResult = aluResult;
    }

    public void printExecutionOutput(int aluResult) {

        if (Global.ID_EXEValid) {
            Logger.println("EX Stage -> result : %d, ", aluResult);
        } else {
            Logger.println("EX Stage -> [NOP]");
        }
    }

}
