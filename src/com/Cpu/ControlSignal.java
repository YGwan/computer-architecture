package com.Cpu;


import com.Logger;

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
    public boolean jr;
    public boolean jal;
    public boolean ori;
    public boolean lui;
    public boolean sll;
    public int aluControl;
    public String inst;

    public void initailcontrolSignal() {
        regDst = false;
        aluSrc = false;
        memToReg = false;
        regWrite = false;
        memRead = false;
        memWrite = false;
        branch = false;
        jump = false;
        jr = false;
        jal = false;
        ori = false;
        lui = false;
        sll = false;
        aluControl = 0;
        inst = "";
    }

    public void setControlSignal(String opcode, String func) {

        switch (opcode) {
            //R타입 instruction
            case "00": {
                switch (func) {
                    case "21": {
                        inst = "ADDU";
                        regDst = true;
                        regWrite = true;
                    }
                    break;
                    case "08": {
                        inst = "JR";
                        jr = true;
                    }
                    break;
                    case "2a": {
                        inst = "SLT";
                        aluControl = 1;
                        regDst = true;
                        regWrite = true;
                    }
                    break;
                    case "23": {
                        inst = "SUBU";
                        aluControl = 4;
                        regDst = true;
                        regWrite = true;
                    }
                    break;
                    case "00": {
                        inst = "SLL";
                        aluControl = 6;
                        sll = true;
                        regDst = true;
                        regWrite = true;
                    }
                    break;
                    default: {
                        Logger.println();
                        throw new IllegalArgumentException("없는 opcode입니다. : " + opcode);
                    }
                }
            }
            break;
            //I타입 instruction
            case "09": {
                inst = "ADDI";
                aluSrc = true;
                regWrite = true;
            }
            break;
            case "2b": {
                inst = "SW";
                aluSrc = true;
                memToReg = true;
                memWrite = true;
            }
            break;
            case "0a": {
                inst = "SLTI";
                aluControl = 1;
                aluSrc = true;
                regWrite = true;
            }
            break;
            case "04": {
                inst = "BEQ";
                aluControl = 3;
                branch = true;
            }
            break;
            case "05": {
                inst = "BNE";
                aluControl = 2;
                branch = true;
            }
            break;
            case "23": {
                inst = "LW";
                aluSrc = true;
                memRead = true;
                memToReg = true;
                regWrite = true;
            }
            break;
            case "0d": {
                inst = "ORI";
                ori = true;
                aluControl = 5;
                regWrite = true;
            }
            break;
            case "0f": {
                inst = "LUI";
                lui = true;
                regWrite = true;
            }
            break;
            //J타입 instruction
            case "02": {
                inst = "JUMP";
                jump = true;
                regWrite = true;
            }
            break;
            case "03": {
                inst = "JAL";
                jal = true;
                regWrite = true;
            }
            break;
            default:
                throw new IllegalArgumentException("없는 opcode입니다. : " + opcode);
        }
    }
}
