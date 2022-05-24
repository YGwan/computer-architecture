package com.Latch;

public class Latches {

    IF_ID[] if_ids = new IF_ID[2];
    ID_EXE[] id_exes = new ID_EXE[2];
    EXE_MEM[] exe_mems = new EXE_MEM[2];
    MEM_WB[] mem_wbs = new MEM_WB[2];

    public void flush() {
        if_ids[1] = if_ids[0];
        id_exes[1] = id_exes[0];
        exe_mems[1] = exe_mems[0];
        mem_wbs[1] = mem_wbs[0];
    }
}
