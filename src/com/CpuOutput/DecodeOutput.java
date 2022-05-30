package com.CpuOutput;

import com.Cpu.ControlSignal;
import com.Logger;
import com.Memory.Global;

import static com.Main.mux;


public class DecodeOutput {

    public ControlSignal controlSignal;
    public String opcode;
    public int rs;
    public int rt;
    public int rd;
    public int shamt;
    public String func;
    public String immediate;
    //추가 구현 변수
    public int signExt;
    public int zeroExt;
    public int loadUpperImm;
    public int jumpAddr;
    public int branchAddr;

    public static DecodeOutput NONE = new DecodeOutput(
            null,
            null,
            0,
            0,
            0,
            0,
            null,
            null,
            0,
            0,
            0,
            0,
            0
    );

    public DecodeOutput(ControlSignal controlSignal, String opcode, int rs, int rt, int rd,
                        int shamt, String func, String immediate, int signExt, int zeroExt, int loadUpperImm, int jumpAddr, int branchAddr) {

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
        this.jumpAddr = jumpAddr;
        this.branchAddr = branchAddr;
    }

    //regDst 처리 부분
    public int regDstSet(boolean ID_EXEValid,ControlSignal controlSignal, int rt, int rd) {

        int regDstResult;
        if(ID_EXEValid) {
            regDstResult = mux(controlSignal.regDst, rd, rt);
            regDstResult = mux(controlSignal.jal, 31, regDstResult);
            return regDstResult;
        } else return 0;
    }

    public void printDecodeStage(boolean IF_IDValid,String opcode, int rs, int rt) {

        if (IF_IDValid) {
            Logger.println("ID Stage -> opcode : %s[%s], rs : R[%d], rt: R[%d]\n", opcode, controlSignal.inst, rs, rt);
        } else {
            Logger.println("ID Stage -> [NOP]");
        }
    }
}
