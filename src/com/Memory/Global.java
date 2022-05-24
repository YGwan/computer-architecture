package com.Memory;

public class Global {


//    ra -> r31
//    fp -> r30
//    sp -> r29

    public static int[] register = new int[32];
    public static int pc;
    public static int nextPC;

    public static boolean IF_IDValid = false;
    public static boolean ID_EXEValid = false;
    public static boolean EXE_MEMValid = false;
    public static boolean MEM_WBValid = false;

    public static boolean InputIF_IDValid = false;
    public static boolean InputID_EXEValid = false;
    public static boolean InputEXE_MEMValid = false;
    public static boolean InputMEM_WBValid = false;

    public static boolean Valid = false;
}
