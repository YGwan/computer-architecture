package com.Cpu;

import com.CpuOutput.RegisterOutput;
import com.Logger;
import com.Memory.Global;

import static com.Main.mux;

/*
 * 레지스터로 들어오는 input
 * */

public class Register {


    public int writeData;


    public RegisterOutput registerCalc(boolean IF_IDValid,int rs, int rt, ControlSignal controlSignal) {

        if(IF_IDValid) {
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

    //WriteBack Stage

    //MemtoReg 처리 부분
    public int memToRegSet(boolean MEM_WBValid,ControlSignal controlSignal, int memoryCalcResult, int aluResult) {
        if (MEM_WBValid) {
            return mux(controlSignal.memToReg, memoryCalcResult, aluResult);
        } return 0;
    }

    public void registerWrite(int nextPC, boolean MEM_WBValid, ControlSignal controlSignal,
                              int memToRegResult, int regDstResult) {

        if(MEM_WBValid) {
            this.writeData = mux(controlSignal.jal, nextPC + 2, memToRegResult);
            if (controlSignal.regWrite) {
                Global.register[regDstResult] = writeData;
            }
        }
    }

    public void printExecutionWriteBack(boolean MEM_WBValid, ControlSignal controlSignal, int regDstResult, int writeData) {

        if (MEM_WBValid) {

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


