package com.Cpu;

import com.CpuOutput.RegisterOutput;
import com.Logger;
import com.Memory.Global;

import static com.Main.mux;

/*
 * 레지스터로 들어오는 input
 * */

public class Register {

    ControlSignal controlSignal;

    private int writeData;
    private int regDstResult;

    public Register(ControlSignal controlSignal) {
        this.controlSignal = controlSignal;
    }

    public RegisterOutput registerCalc(int rs, int rt, int writeRegister) {
        this.regDstResult = writeRegister;

        return new RegisterOutput(
                controlSignal,
                Global.register[rs],
                Global.register[rt]
        );
    }

    public void registerWrite(int memToRegResult) {
        this.writeData = mux(controlSignal.jal, Global.pc + 2, memToRegResult);
        if (controlSignal.regWrite) {
            Global.register[regDstResult] = writeData;
        }
    }

    public void printExecutionWriteBack() {
        if (controlSignal.regWrite) {
            if (controlSignal.jal) {
                Logger.println("WB Stage -> R[%d] = %d\n", regDstResult, writeData * 4);
            } else {
                Logger.println("WB Stage -> R[%d] = %d\n", regDstResult, writeData);
            }
            Global.register[regDstResult] = writeData;
        } else {
            Logger.println("WB Stage -> ");
        }
    }

}


