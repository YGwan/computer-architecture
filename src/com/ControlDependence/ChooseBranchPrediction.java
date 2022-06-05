package com.ControlDependence;

import com.Memory.Global;

public class ChooseBranchPrediction {

    public static boolean onAlwaysTaken;
    public static boolean onAlwaysNotTaken;

    public void chooseBP(boolean onAlwaysTaken, boolean onAlwaysNotTaken) {

        if(Global.onBranchPrediction) {
            ChooseBranchPrediction.onAlwaysTaken = onAlwaysTaken;
            ChooseBranchPrediction.onAlwaysNotTaken = onAlwaysNotTaken;

        } else {
            ChooseBranchPrediction.onAlwaysTaken = false;
            ChooseBranchPrediction.onAlwaysNotTaken = false;
        }
    }
}
