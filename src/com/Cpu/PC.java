package com.Cpu;
import com.Logger;

import static com.Main.mux;
import static com.Memory.Global.pc;

public class PC {

    //pc 설정 부분
    public void pcJumpJalJrUpdate(boolean IF_IDValid, int nextPc, ControlSignal controlSignal, int inputReadData1, int decodeJumpAddr) {
        pc = nextPc;
        if(IF_IDValid) {
            pc = mux(controlSignal.jump, decodeJumpAddr / 4, pc);
            pc = mux(controlSignal.jal, decodeJumpAddr/4 , pc);
            pc = mux(controlSignal.jr, inputReadData1, pc);
        }
    }

    public void bneBeqPcUpdate(boolean ID_EXEValid, ControlSignal controlSignal, int nextPC, int aluResult, int branchAddr) {

        if (ID_EXEValid) {
            pc = mux(bneBeqProcess(aluResult, controlSignal), ((nextPC * 4 + branchAddr) / 4), pc);
        }
    }

    public void pcUpdatePrint(boolean ID_EXEValid) {
        if (ID_EXEValid) {
            String pcHex;
            if (pc == -1) {
                pcHex = Integer.toHexString(pc);
            } else {
                pcHex = Integer.toHexString(pc * 4);
            }
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
