package com;


public class Logger {

    public static boolean LOGGING_SIGNAL = true;

    public static void println() {
        if (!LOGGING_SIGNAL) return;
        System.out.println();
    }

    public static void println(String msg) {
        if (!LOGGING_SIGNAL) return;
        System.out.println(msg);
    }

    public static void println(String msg, Object... args) {
        if (!LOGGING_SIGNAL) return;
        System.out.printf(msg, args);
    }
}
