package com.Memory;

public class Global {


//    ra -> r31
//    fp -> r30
//    sp -> r29

    public static int[] register = new int[32];
    public static int pc;
    public static int nextPC;

    public static void init() {
        pc =0;
        nextPC =0;
    }

}
