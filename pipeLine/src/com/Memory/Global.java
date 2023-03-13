package com.Memory;

public class Global {


//    ra -> r31
//    fp -> r30
//    sp -> r29

    public static int[] register = new int[32];

    public static boolean onBranchPrediction;
    public static float branchAllCount;
    public static float branchCorrectCount;

    public static void initializedRegister() {
        register[29] = 0x1000000;
        register[31] = 0xFFFFFFFF;
        branchAllCount = 0;
        branchCorrectCount = 0;
    }

}