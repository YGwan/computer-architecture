package com.ControlDependence;

import com.Cpu.ControlSignal;
import com.CpuOutput.AluOutput;
import com.CpuOutput.DecodeOutput;

import java.util.Objects;

public class Stalling {

    public static boolean stallingMethod(boolean if_idValid, boolean id_exeValid, boolean fetchValid,
                                         DecodeOutput decodeOutput, ControlSignal controlSignal) {
        if(if_idValid) {
            if(Objects.equals(decodeOutput.controlSignal.inst, "BNE")) {
                if(id_exeValid) {
                    fetchValid = Objects.equals(controlSignal.inst, "BNE");
                }
            } else if (Objects.equals(decodeOutput.controlSignal.inst, "BEQ")) {
                if(id_exeValid) {
                    fetchValid = Objects.equals(controlSignal.inst, "BEQ");
                }
            } else {
                fetchValid = true;
            }
        }
        return fetchValid;
    }
}
