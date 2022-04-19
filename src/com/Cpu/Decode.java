package com.Cpu;

import static com.Main.mux;

/*
 * 명령어 처리 - 구간 별로 나누기
 * RegDst mux 구현
 * */
public class Decode {

    ControlSignal controlSignal;

    public Decode(ControlSignal controlSignal) {
        this.controlSignal = controlSignal;
    }

    public DecodeOutput decodeInstruction(String binaryInstruction) {

        //변수 할당 부분
        String opcode = checkOpcodeOrFunc(binaryInstruction.substring(0, 6));
        int rs = readTobinaryString(binaryInstruction.substring(6, 11));
        int rt = readTobinaryString(binaryInstruction.substring(11, 16));
        int rd = readTobinaryString(binaryInstruction.substring(16, 21));
        int shamt = readTobinaryString(binaryInstruction.substring(21, 26));
        String func = checkOpcodeOrFunc(binaryInstruction.substring(26, 32));
        int signExt = setSignExtImm(binaryInstruction);

        //controlSignal 초기화 작업
        this.controlSignal.initailcontrolSignal();
        this.controlSignal.setControlSignal(opcode, func);


        return new DecodeOutput(
                controlSignal,
                opcode,
                rs,
                rt,
                rd,
                shamt,
                func,
                signExt
        );
    }

    //signExt 만들기
    public int setSignExtImm(String binaryInstruction) {
        //SignExtImm = { 16{immediate[15]}, immediate}
        char firstImmeBit = binaryInstruction.charAt(16);
        StringBuilder signExtImmBinaryString = new StringBuilder();

        //16{immediate[15]}
        for (int bitIndex = 0; bitIndex < 16; bitIndex++) {
            signExtImmBinaryString.append(firstImmeBit);
        }
        // + immediate
        signExtImmBinaryString.append(binaryInstruction.substring(16, 32));

        // 보수 처리 할지 말지 구현
        if (firstImmeBit == '1') {
            return readToNegativebinaryString(signExtImmBinaryString.toString());
        } else {
            return readTobinaryString(signExtImmBinaryString.toString());
        }
    }

    // 연산에 필요한 함수 구현 (parsing 함수 부분)
    //2진수를 16진수로 변환
    public static String binTohex(String instruction) {
        int dec = Integer.parseInt(instruction, 2);
        return Integer.toString(dec, 16);
    }

    //2진수를 10진수로 변환
    public int readTobinaryString(String instruction) {
        return Integer.parseInt(instruction, 2);
    }

    //음수 2진수를 10진수로 변환 -> 보수작업 추가
    public int readToNegativebinaryString(String instruction) {
        return Integer.parseUnsignedInt(instruction, 2);
    }

    //opcode 변환
    public String checkOpcodeOrFunc(String partOfinstruction) {
        String opcode1 = binTohex(partOfinstruction.substring(0, 2));
        String opcode2 = binTohex(partOfinstruction.substring(2, 6));
        return opcode1 + opcode2;
    }
}
