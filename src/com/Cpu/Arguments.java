package com.Cpu;

public class Arguments {

    public String opcode;
    public int rs;
    public int rt;
    public int rd;
    public int shamt;
    public String func;
    public int signExt;

    public Arguments(String opcode, int rs, int rt, int rd, int shamt, String func, int signExt) {
        this.opcode = opcode;
        this.rs = rs;
        this.rt = rt;
        this.rd = rd;
        this.shamt = shamt;
        this.func = func;
        this.signExt = signExt;
    }
}
