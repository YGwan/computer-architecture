package com.CpuOutput;

import com.Cpu.ControlSignal;
import com.Logger;

public class RegisterOutput {

    ControlSignal controlSignal;
    public int firstRegisterOutput;
    public int secondRegisterOutput;

    public RegisterOutput(ControlSignal controlSignal, int firstRegisterOutput, int secondRegisterOutput) {
        this.controlSignal = controlSignal;
        this.firstRegisterOutput = firstRegisterOutput;
        this.secondRegisterOutput = secondRegisterOutput;
    }

    public void printDecodeStage(boolean IF_IDValid,String opcode, int rs, int rt, int readData1, int readData2) {

        if (IF_IDValid) {
            Logger.println("ID Stage -> opcode : %s[%s], rs : R[%d], rt: R[%d], readData1 = %d, readData2 = %d\n",
                    opcode, controlSignal.inst, rs, rt, readData1, readData2);
        } else {
            Logger.println("ID Stage -> [NOP]");
        }
    }
}