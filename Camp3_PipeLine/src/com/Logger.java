package com;


public class Logger {

    public static boolean LOGGING_SIGNAL = true;
    public static boolean LOGGING_COUNTER_SIGNAL = true;
    public static int min = 0;
    public static int max = 0;

    public static void setPrintRange(int cycle) {
        LOGGING_SIGNAL = min < cycle && cycle < max;
    }

    public static void println() {
        if (!LOGGING_SIGNAL) return;
        System.out.println();
    }

    public static void countPrintln(String msg, Object... args) {
        if (!LOGGING_COUNTER_SIGNAL) return;
        System.out.printf(msg, args);
    }

    public static void println(String msg) {
        if (!LOGGING_SIGNAL) return;
        System.out.println(msg);
    }

    public static void println(String msg, Object... args) {
        if (!LOGGING_SIGNAL) return;
        System.out.printf(msg, args);
    }

    public static void print(String msg) {
        if (!LOGGING_SIGNAL) return;
        System.out.print(msg);
    }
}
