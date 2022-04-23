package com.Cpu;

import com.CpuOutput.RegisterOutput;
import com.Memory.Global;

/*
 * 레지스터로 들어오는 input
 * */

public class Register {

    ControlSignal controlSignal;

    public Register(ControlSignal controlSignal) {
        this.controlSignal = controlSignal;
    }

    public RegisterOutput registerCalc(String opcode, int rs, int rt, int writeRegister, String func) {

        //ALUSrc 부분

        //switch구문은 나중에 사라져도 된다. -> 일단 확인용
        switch (opcode) {
            //R타입 instruction
            case "00": {
                switch (func) {
                    //move or addu
                    case "21": {
                        System.out.println("move 시작");
                    }
                    break;

                    case "08": {
                        System.out.println("jr 시작");
                    }
                    break;

                    default: {
                        System.out.println("0");
                    }
                }


            }
            break;

            //I타입 instruction
            //addi
            case "09": {
                System.out.println("addi 시작");
            }
            break;

            //sw
            case "2b": {
                System.out.println("sw 시작");
            }
            break;

            case "23": {
                System.out.println("lw 시작");
            }
            break;
            //J타입 instruction
        }

        return new RegisterOutput(
                controlSignal,
                Global.register[rs],
                Global.register[rt]
        );
    }

    public void registerWrite(int regDstResult, int memToRegResult) {
        if (controlSignal.regWrite) {
            Global.register[regDstResult] = memToRegResult;
        }
    }

}


