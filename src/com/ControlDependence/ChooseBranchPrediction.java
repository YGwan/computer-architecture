package com.ControlDependence;

import com.Memory.Global;

public class ChooseBranchPrediction {

    public static boolean onAlwaysTaken;
    public static boolean onAlwaysNotTaken;
    public static boolean onOneBitPrediction;
    public static boolean onTwoBitPrediction;

    public void chooseBP(boolean onAlwaysTaken, boolean onAlwaysNotTaken, boolean onOneBitPrediction) {

        if(Global.onBranchPrediction) {
            ChooseBranchPrediction.onAlwaysTaken = onAlwaysTaken;
            ChooseBranchPrediction.onAlwaysNotTaken = onAlwaysNotTaken;
            ChooseBranchPrediction.onOneBitPrediction = onOneBitPrediction;

        } else {
            ChooseBranchPrediction.onAlwaysTaken = false;
            ChooseBranchPrediction.onAlwaysNotTaken = false;
            ChooseBranchPrediction.onOneBitPrediction = false;
        }
    }
}
