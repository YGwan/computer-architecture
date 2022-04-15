package com.Cpu;

import com.Memory.Global;

public class Register {
    String binaryInstruction;

    public Register(String binaryInstruction) {
        this.binaryInstruction = binaryInstruction;
    }

    String opcode;
    int rs;
    int rt;
    int rd;
    int shamt;
    String func;

    //추가 구현 변수
    int writeReg;
    int signExt;
    int aluResult;

    public void decodeInstruction() {

        //변수 할당 부분
        this.opcode = checkOpcodeOrFunc(binaryInstruction.substring(0, 6));
//        System.out.println(opcode);
        this.rs = readTobinaryString(this.binaryInstruction.substring(6, 11));
        this.rt = readTobinaryString(this.binaryInstruction.substring(11, 16));
        this.rd = readTobinaryString(this.binaryInstruction.substring(16, 21));
        this.shamt = readTobinaryString(this.binaryInstruction.substring(21, 26));
        this.func = checkOpcodeOrFunc(this.binaryInstruction.substring(26, 32));
        //System.out.println(func);
        this.signExt = setSignExtImm();

        //클래스 선언
        Arguments arguments = new Arguments(opcode, rs, rt, rd, shamt, func, signExt);
        ControlSignal controlSignal = new ControlSignal(opcode,func);
        controlSignal.setControlSignal(opcode,func);
        ALU alu = new ALU(rs, rt, rd, controlSignal);

        //타입 별로 나누기
        /*1. RegDst 정하기
        * 2. alu 연산 값 구하기(write data 구하기)
        * 3. RegWrite 하기(레지스터 값을 갱신할지 안할지 정하기)
        * */



        //R타입 instruction
        if (opcode.equals("00")) {
            switch (func) {

                //move or addu
                case "21": {
                    System.out.println("move 시작");
                    writeReg = writeRegister(controlSignal.RegDst);
                    aluResult = alu.process(aluTwoInputRegister(controlSignal.ALUSrc));
                    writeValueToReg(controlSignal.RegWrite, aluResult);
                } break;

            }
        }

        //I타입 instruction
        switch (opcode) {

            //addi
            case "09": {
                System.out.println("addi 시작");
                writeReg =  writeRegister(controlSignal.RegDst);
                aluResult = alu.process(aluTwoInputRegister(controlSignal.ALUSrc));
                writeValueToReg(controlSignal.RegWrite, aluResult);
            } break;

            //sw
            case "2b": {
                System.out.println("sw 시작");
                writeReg =  writeRegister(controlSignal.RegDst);
                aluResult = alu.process(aluTwoInputRegister(controlSignal.ALUSrc)); //address


            }
        }

        //J타입 instruction


    }
    //ALUSrc
    private int aluTwoInputRegister(boolean ALUSrc) {
        if(ALUSrc) {
            return this.signExt;

        } else {
            return this.rt;
        }
    }

    //RegDst
    private int writeRegister(boolean RegDst) {
        if(RegDst) {
            return this.rd;
        } else {
            return this.rt;
        }
    }

    //writeValueToReg
    private void writeValueToReg(boolean RegWrite, int result) {
        if(RegWrite) {
            Global.register[writeReg] = result;
        } else {

        }
    }

    //sign
    public int setSignExtImm() {
        //SignExtImm = { 16{immediate[15]}, immediate}
        char firstImmeBit = binaryInstruction.charAt(16);
        String signExtImmBinaryString = "";

        //16{immediate[15]}
        for(int bitIndex =0; bitIndex<16; bitIndex++) {
            signExtImmBinaryString += firstImmeBit;
        }
        // + immediate
        signExtImmBinaryString += binaryInstruction.substring(16,32);

        // 보수 처리 할지 말지 구현
        if (firstImmeBit == '1') {
            return readToNegativebinaryString(signExtImmBinaryString);
        } else {
            return readTobinaryString(signExtImmBinaryString);
        }
    }

    // 연산에 필요한 함수 구현 (parsing 함수 부분)

    //2진수를 16진수로 변환
    public static String binTohex(String instruction) {
        int dec = Integer.parseInt(instruction,2);
        String hex = Integer.toString(dec, 16);
        return hex;
    }

    //2진수를 10진수로 변환
    public static int readTobinaryString(String instruction) {
        return Integer.parseInt(instruction,2);
    }

    //음수 2진수를 10진수로 변환 -> 보수작업 추가
    public static int readToNegativebinaryString(String instruction) {
        return Integer.parseUnsignedInt(instruction,2);
    }

    //opcode 변환
    public static String checkOpcodeOrFunc(String partOfinstruction) {
        String opcode1 = binTohex(partOfinstruction.substring(0, 2));
        String opcode2 = binTohex(partOfinstruction.substring(2, 6));
        return opcode1 + opcode2;
    }

    //func변환

}
