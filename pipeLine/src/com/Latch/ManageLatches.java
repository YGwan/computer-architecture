package com.Latch;

public class ManageLatches {

    protected static IF_ID if_id;
    protected static ID_EXE id_exe;
    protected static EXE_MEM exe_mem;
    protected static MEM_WB mem_wb;

    protected static void declareLatches() {
        if_id = new IF_ID();
        id_exe = new ID_EXE();
        exe_mem = new EXE_MEM();
        mem_wb = new MEM_WB();
    }

    public static void initializeValidBits(IF_ID if_id, ID_EXE id_exe, EXE_MEM exe_mem, MEM_WB mem_wb) {
        if_id.valid = false;
        id_exe.valid = false;
        exe_mem.valid = false;
        mem_wb.valid = false;
    }

    protected static void flush() {
        if_id.output();
        id_exe.output();
        exe_mem.output();
        mem_wb.output();
    }
}
