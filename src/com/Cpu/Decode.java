package com.Cpu;

import com.CpuOutput.DecodeOutput;
import com.Memory.Global;

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

        this.controlSignal.initailcontrolSignal();

        if (Global.IF_IDValid) {

            //변수 할당 부분
            String opcode = checkOpcodeOrFunc(binaryInstruction.substring(0, 6));
            int rs = readTobinaryString(binaryInstruction.substring(6, 11));
            int rt = readTobinaryString(binaryInstruction.substring(11, 16));
            int rd = readTobinaryString(binaryInstruction.substring(16, 21));
            int shamt = readTobinaryString(binaryInstruction.substring(21, 26));
            String func = checkOpcodeOrFunc(binaryInstruction.substring(26, 32));
            int signExt = setSignExtImm(binaryInstruction);
            int zeroExt = setZeroExt(binaryInstruction);
            int loadUpperImm = setLoadUpperImm(binaryInstruction);

            //controlSignal 초기화 작업
            this.controlSignal.setControlSignal(opcode, func);
            Global.InputID_EXEValid = true;

            return new DecodeOutput(
                    controlSignal,
                    opcode,
                    rs,
                    rt,
                    rd,
                    shamt,
                    func,
                    signExt,
                    zeroExt,
                    loadUpperImm
            );
        } else return new DecodeOutput(
                controlSignal,
                null,
                0,
                0,
                0,
                0,
                null,
                0,
                0,
                0

        );
    }
    //signExt 만들기
    //SignExtImm = { 16{immediate[15]}, immediate
    public int setSignExtImm(String binaryInstruction) {
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

    //zeroExt 만들기
    //ZeroExtImm = { 16{1b’0}, immediate }
    public int setZeroExt(String binaryInstruction) {
        StringBuilder zeroExtImmBinaryString = new StringBuilder();
        zeroExtImmBinaryString.append("0000000000000000");
        zeroExtImmBinaryString.append(binaryInstruction.substring(16, 32));
        return readTobinaryString(zeroExtImmBinaryString.toString());
    }

    //Load Upper Imm 만들기
    //{imm, 16’b0}
    public int setLoadUpperImm(String binaryInstruction) {
        StringBuilder loadUpperImmBinaryString = new StringBuilder();
        loadUpperImmBinaryString.append(binaryInstruction.substring(16, 32));
        loadUpperImmBinaryString.append("0000000000000000");

        char firstImmeBit = binaryInstruction.charAt(16);
        // 보수 처리 할지 말지 구현
        if (firstImmeBit == '1') {
            return readToNegativebinaryString(loadUpperImmBinaryString.toString());
        } else {
            return readTobinaryString(loadUpperImmBinaryString.toString());
        }
    }

    // 연산에 필요한 함수 구현 (parsing 함수 부분)
    //2진수를 16진수로 변환
    private static String binTohex(String instruction) {
        int dec = Integer.parseInt(instruction, 2);
        return Integer.toString(dec, 16);
    }

    //2진수를 10진수로 변환
    public int readTobinaryString(String instruction) {
        return Integer.parseInt(instruction, 2);
    }

    //음수 2진수를 10진수로 변환 -> 보수 작업 추가
    private int readToNegativebinaryString(String instruction) {
        return Integer.parseUnsignedInt(instruction, 2);
    }

    //opcode 변환
    private String checkOpcodeOrFunc(String partOfinstruction) {
        String opcode1 = binTohex(partOfinstruction.substring(0, 2));
        String opcode2 = binTohex(partOfinstruction.substring(2, 6));
        return opcode1 + opcode2;
    }
}
