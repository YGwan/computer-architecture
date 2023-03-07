package com.ControlDependence;

public class OneBitPrediction implements branchController {

    public boolean checkBit = true;
    @Override
    public boolean taken() {
        return checkBit;
    }
}
