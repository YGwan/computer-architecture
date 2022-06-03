package com.ControlDependence;

import com.CpuOutput.AluOutput;
import com.CpuOutput.DecodeOutput;
import com.Latch.IF_ID;

import java.util.Objects;

public class Stalling {

    public static boolean stallingMethod(boolean if_idValid, boolean fetchValid, DecodeOutput decodeOutput, AluOutput aluOutput) {
        if(if_idValid) {
            if(Objects.equals(decodeOutput.controlSignal.inst, "BNE")) {
                if(aluOutput.aluResult == 1) {
                    fetchValid = false;
                }
            } else if (Objects.equals(decodeOutput.controlSignal.inst, "BEQ")) {
                if(aluOutput.aluResult == 0) {
                    fetchValid = false;
                }
            } else {
                fetchValid = true;
            }
        }
        return fetchValid;
    }
}
