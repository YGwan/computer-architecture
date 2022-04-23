package com.CpuOutput;
import com.Cpu.ControlSignal;

import static com.Main.mux;


public class DecodeOutput {

    ControlSignal controlSignal;
    public String opcode;
    public int rs;
    public int rt;
    public int rd;
    public int shamt;
    public String func;
    //추가 구현 변수
    public int regDstResult; //rs, rd 선택
    public int signExt;

    public DecodeOutput(ControlSignal controlSignal,String opcode, int rs, int rt, int rd, int shamt, String func, int signExt) {
        this.controlSignal = controlSignal;
        this.opcode = opcode;
        this.rs = rs;
        this.rt = rt;
        this.rd = rd;
        this.shamt = shamt;
        this.func = func;
        this.signExt = signExt;
        set();
    }

    //regDst 처리 부분
    private void set() {
        regDstResult = mux(controlSignal.regDst, rd, rt);
    }

    public void printDecodeStage() {
        System.out.printf("ID Stage -> opcode : %s, rs : R[%d], rt: R[%d] ======> ", opcode, rs,rt);
    }
}
