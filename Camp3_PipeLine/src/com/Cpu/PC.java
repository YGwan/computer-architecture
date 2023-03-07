package com.Cpu;

import com.ControlDependence.ChooseBranchPrediction;
import com.Logger;

import static com.Main.mux;

public class PC extends ChooseBranchPrediction {

    //pc 설정 부분
    public int pcJumpJalJrUpdate(boolean IF_IDValid, int pc, ControlSignal controlSignal, int inputReadData1, int decodeJumpAddr) {
        if (IF_IDValid) {

            int pc1 = mux(controlSignal.jump, decodeJumpAddr / 4, pc);
            int pc2 = mux(controlSignal.jal, decodeJumpAddr / 4, pc1);
            return mux(controlSignal.jr, inputReadData1, pc2);
        }
        return pc;
    }

    //Exe단계에서 할때랑 Decode단계에서 할때랑 분리

    public int bneBeqPcUpdate(boolean ID_EXEValid, ControlSignal controlSignal, int pc, int id_exePc, int aluResult, int branchAddr) {

        if (ID_EXEValid) {
            return mux(bneBeqProcess(aluResult, controlSignal), (((id_exePc + 1) * 4 + branchAddr) / 4), pc);
        }
        return pc;
    }

    public int AlwaysTakenPcUpdate(boolean IF_IDValid, ControlSignal controlSignal, int pc, int nextPc) {

        if(IF_IDValid) {
            return mux(controlSignal.branch, nextPc, pc);
        }
        return pc;

    }


    public void pcUpdatePrint(boolean ID_EXEValid, int pc) {
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

    //bne - beq 처리
    public boolean bneBeqProcess(int aluResult, ControlSignal controlSignal) {
        boolean boolValue;
        boolValue = aluResult == 1;
        return boolValue && controlSignal.branch;
    }
}
