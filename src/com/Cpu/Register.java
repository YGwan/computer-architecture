package com.Cpu;

import com.CpuOutput.RegisterOutput;
import com.Logger;
import com.Memory.Global;

import static com.Main.mux;

/*
 * 레지스터로 들어오는 input
 * */

public class Register {


    private int writeData;


    public RegisterOutput registerCalc(int rs, int rt, ControlSignal controlSignal) {


        if(Global.IF_IDValid) {
            return new RegisterOutput(
                    controlSignal,
                    Global.register[rs],
                    Global.register[rt]
            );
        } else {
            return new RegisterOutput(
                    null,
                    0,
                    0
            );
        }
    }

    public void registerWrite(ControlSignal controlSignal, int memToRegResult, int regDstResult) {

        if(Global.MEM_WBValid) {
            this.writeData = mux(controlSignal.jal, Global.pc + 2, memToRegResult);
            if (controlSignal.regWrite) {
                Global.register[regDstResult] = writeData;
            }
        }
    }

    public void printExecutionWriteBack(ControlSignal controlSignal, int regDstResult) {

        if (Global.MEM_WBValid) {

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
        } else {
            Logger.println("WB Stage -> [NOP]");
        }
    }

}


