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
    public int aluControl;

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
        aluControl = 0;
    }


    public void setControlSignal(String opcode, String func) {

        switch (opcode) {
            //R타입 instruction
            case "00": {
                switch (func) {

                    case "21": {
                        Logger.println("(ADDU)");
                        regDst = true;
                        regWrite = true;

                    }
                    break;

                    case "08": {
                        Logger.println("(jr)");
                        jr = true;

                    }
                    break;

                    case "2a": {
                        Logger.println("(SLT)");
                        aluControl = 1;
                        regDst = true;
                        regWrite = true;

                    }
                    break;

                    case "23": {
                        Logger.println("(SUBU)");
                        aluControl = 4;
                        regDst = true;
                        regWrite = true;
                    }
                    break;

                    default: {
                        Logger.println();
                        throw new IllegalArgumentException("없는 opcode입니다. : "+ opcode);
                    }
                }
            }
            break;

            //I타입 instruction
            case "09": {
                Logger.println("(ADDI)");
                aluSrc = true;
                regWrite = true;
            }
            break;

            case "2b": {
                Logger.println("(SW)");
                aluSrc = true;
                memToReg = true;
                memWrite = true;
            }
            break;

            case "0a": {
                Logger.println("(SLTI)");
                aluControl = 1;
                aluSrc = true;
                regWrite = true;
            }
            break;

            case "04": {
                Logger.println("(BEQ)");
                aluControl = 3;
                branch = true;
            }
            break;

            case "05": {
                Logger.println("(BNE)");
                aluControl = 2;
                branch = true;
            }
            break;

            case "23": {
                Logger.println("(LW)");
                aluSrc = true;
                memRead = true;
                memToReg = true;
                regWrite = true;
            }
            break;

            case "0d": {
                Logger.println("(ORI)");
                ori = true;
                aluControl = 5;
                regWrite = true;
            }
            break;

            //J타입 instruction

            case "02": {
                Logger.println("(JUMP)");
                jump = true;
                regWrite = true;
            }
            break;

            case "03": {
                Logger.println("(JAL)");
                jal = true;
                regWrite = true;
            }
            break;

            default:
                throw new IllegalArgumentException("없는 opcode입니다. : "+ opcode);
        }
    }
}
