package com.CpuOutput;

import com.Cpu.ControlSignal;
import com.Logger;
import com.Memory.Global;

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
    public int zeroExt;
    public int loadUpperImm;

    public DecodeOutput(ControlSignal controlSignal, String opcode, int rs, int rt, int rd,
                        int shamt, String func, int signExt, int zeroExt, int loadUpperImm) {

        this.controlSignal = controlSignal;
        this.opcode = opcode;
        this.rs = rs;
        this.rt = rt;
        this.rd = rd;
        this.shamt = shamt;
        this.func = func;
        this.signExt = signExt;
        this.zeroExt = zeroExt;
        this.loadUpperImm = loadUpperImm;
        set();
    }

    //regDst 처리 부분
    private void set() {
        regDstResult = mux(controlSignal.regDst, rd, rt);
        regDstResult = mux(controlSignal.jal, 31, regDstResult);
    }

    public void printDecodeStage() {

        if(Global.IF_IDValid) {
            Logger.println("ID Stage -> opcode : %s, rs : R[%d], rt: R[%d]\n", opcode, rs, rt);
        } else  {
            Logger.println("ID Stage -> NOP");
        }
    }
}
