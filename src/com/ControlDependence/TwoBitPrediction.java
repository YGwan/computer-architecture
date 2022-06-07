package com.ControlDependence;

public class TwoBitPrediction implements branchController {

    public int chance = 1;
    public boolean checkBit;

    @Override
    public boolean taken() {

        if(chance > 2) {
            chance = 2;
        }  else if (chance < -1) {
            chance = -1;
        }

        checkBit = chance > 0;

        return checkBit;
    }
}
