package com.Cpu;


public class ControlSignal {
    //LUI, ORI, SLTI, ADDU, J, LW, BEQ, SLL, JAL, ADDI, BNE, SLT, SLTU, ADDIU

    public boolean RegDst;
    public boolean SignEx;
    public boolean ALUSrc;
    public boolean MemtoReg;
    public boolean RegWrite;
    public boolean MemRead;
    public boolean memWrite;
    public boolean Branch;
    public boolean Jump;
    public boolean JumpReg;
    public boolean JumpLink;
    public boolean ALUOp;

    String opcode;
    String func;

    public ControlSignal(String opcode, String func) {
        this.opcode = opcode;
        this.func = func;
    }

    public ControlSignal() {
    }

    public void setControlSignal(String opcode, String func) {



        //R-type instruction
        if(opcode.equals("00")) {
            //ADDU
            switch (func){
                case "21" : {
                    RegDst = true;
                    ALUSrc = false;
                    RegWrite = true;
                    memWrite = false;
                } break;

            }
        }
        //I-type instruction
        //addi
        else if(opcode.equals("09")) {
            RegDst = false;
            ALUSrc = true;
            RegWrite = true;
            memWrite = false;
        }
        //sw
        else if(opcode.equals("2b")) {
            //RegDst = true; -> 아무거나 상관 없음
            ALUSrc = true;
            RegWrite = false;
            memWrite = true;
        }
    }

}
