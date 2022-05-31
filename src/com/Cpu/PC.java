package com.Cpu;

import com.Logger;

import static com.Main.mux;
import static com.Memory.Global.pc;

public class PC {

    private String pcHex;

    //pc 설정 부분
    public void pcUpdate(boolean ID_EXEValid, ControlSignal controlSignal, int nextPC, int rsValue, int aluResult, int jumpAddr, int branchAddr) {

        if (ID_EXEValid) {

            pc = mux(controlSignal.jr, rsValue, pc);
            pc = mux(controlSignal.jump, jumpAddr / 4, pc);
            pc = mux(bneBeqProcess(aluResult, controlSignal), ((nextPC * 4 + branchAddr) / 4), pc);
            pc = mux(controlSignal.jal, jumpAddr / 4, pc);

            if (pc == -1) {
                pcHex = Integer.toHexString(pc);
            } else {
                pcHex = Integer.toHexString(pc * 4);
            }

        }
    }

    public void pcUpdatePrint(boolean ID_EXEValid) {
        if (ID_EXEValid) {
            Logger.println("Next  PC -> 0x" + pcHex);
        }
    }

    //bne 처리
    private boolean bneBeqProcess(int aluResult, ControlSignal controlSignal) {
        boolean boolValue;
        if (aluResult == 1) {
            boolValue = true;
        } else boolValue = false;

        if (boolValue && controlSignal.branch) {
            return true;
        } else {
            return false;
        }
    }
}
