package com.Memory;

public class Global {


//    ra -> r31
//    fp -> r30
//    sp -> r29

    public static int[] register = new int[32];
    public static int pc;

    public static int[] dataMemory = new int[0x1000000];

}
