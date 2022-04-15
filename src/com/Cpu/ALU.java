package com.Cpu;


import com.Memory.Memory;
import com.Memory.Global;

public class ALU {

    int rs;
    int rt;
    int rd;
    ControlSignal controlSignal;
    int aluResult;
    int writeDate;

    public ALU(int rs, int rt, int rd, ControlSignal controlSignal) {
        this.rs = rs;
        this.rt = rt;
        this.rd = rd;
        this.controlSignal = controlSignal;
    }

    //ALU 기본 연산
    int process(int aluSrcResult) {

        aluResult = Global.register[rs] + aluSrcResult;
        writeDate = rt;
        Memory memory = new Memory(aluResult, writeDate, controlSignal);
        memory.accessMem();
        return aluResult;
    }
}
