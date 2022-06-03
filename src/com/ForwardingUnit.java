package com;

import com.Cpu.ControlSignal;

public class ForwardingUnit {

    public int forward(boolean EXE_MEMValid, boolean MEM_WBValid, ControlSignal exeMemControlSignal, ControlSignal memWbControlSignal,
                        int exeMemRD, int targetRegister, int memWbRD) {

        if (EXE_MEMValid) {
            if ((exeMemControlSignal.regWrite) && (exeMemRD != 0) && (exeMemRD == targetRegister)) {
                return 1;
            }
        }

        if (MEM_WBValid) {
            if ((memWbControlSignal.regWrite) && (memWbRD != 0) && (memWbRD == targetRegister)) {
                return 2;
            }
        }
        return 0;
    }
}
