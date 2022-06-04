package com.ControlDependence;

import com.Cpu.ControlSignal;

import static com.Main.mux;


public class AlwaysTaken {

    public int alwaysTaken(boolean if_idValid, ControlSignal controlSignal, int pc , int stagePc, int branchAddr) {

        if (if_idValid) {
            return mux(controlSignal.branch, (((stagePc+1) * 4 + branchAddr) / 4), pc);
        }
        return pc;
    }
}
