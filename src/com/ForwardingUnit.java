package com;

import com.Cpu.ControlSignal;

public class ForwardingUnit {


    public int forwardA(boolean EXE_MEMValid, boolean MEM_WBValid, ControlSignal exeMemControlSignal, ControlSignal memWbControlSignal,
                        int exeMemRD, int idExeRS, int memWbRD) {

        if (EXE_MEMValid) {
            if ((exeMemControlSignal.regWrite) && (exeMemRD != 0) && (exeMemRD == idExeRS)) {
                return 1;
            }
        }

        if (MEM_WBValid) {
            if ((memWbControlSignal.regWrite) && (memWbRD != 0) && (memWbRD == idExeRS)) {
                if ((exeMemRD != idExeRS) || (!exeMemControlSignal.regWrite)) {
                    return 2;
                }
            }
        }

        return 0;
    }
    public int forwardB(boolean EXE_MEMValid, boolean MEM_WBValid, ControlSignal exeMemControlSignal, ControlSignal memWbControlSignal,
                        int exeMemRD, int idExeRT, int memWbRD, int idExeRS) {


        if (EXE_MEMValid) {
            if (exeMemControlSignal.regWrite && (exeMemRD != 0) && (exeMemRD == idExeRT)) {
                return 1;
            }
        }

        if (MEM_WBValid) {

            if (memWbControlSignal.regWrite && (memWbRD != 0) && (memWbRD == idExeRT)) {
                if ((exeMemRD != idExeRS) || (!exeMemControlSignal.regWrite)) {
                   return 2;
                }
            }
        }
        return 0;
    }
}
