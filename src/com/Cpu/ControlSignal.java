package com.Cpu;


public class ControlSignal {
    //LUI, ORI, SLTI, ADDU, J, LW, BEQ, SLL, JAL, ADDI, BNE, SLT, SLTU, ADDIU

    public boolean regDst;
    public boolean aluSrc;
    public boolean memToReg;
    public boolean regWrite;
    public boolean memRead;
    public boolean memWrite;
    public boolean branch;
    public boolean jump;
    public boolean jumpReg;
    public boolean jumpLink;
    public boolean aluOp;

    public void initailcontrolSignal() {
        regDst = false;
        aluSrc = false;
        memToReg = false;
        regWrite = false;
        memRead = false;
        memWrite = false;
    }


    public void setControlSignal(String opcode, String func) {

        switch (opcode) {
            //R타입 instruction
            case "00": {
                switch (func) {
                    //move or addu
                    case "21": {
                        regDst = true;
                        regWrite = true;

                    }
                    break;

                    case "08": {
                    }
                }
            }
            break;

            //I타입 instruction
            //addi
            case "09": {
                aluSrc = true;
                regWrite = true;
            }
            break;

            //sw
            case "2b": {
                aluSrc = true;
                memToReg = true;
                memWrite = true;
            }
            break;

            //lw
            case "23": {
                aluSrc = true;
                memRead = true;
                memToReg = true;
                regWrite = true;
            }
            //J타입 instruction
        }
        //R-type instruction

    }

}
