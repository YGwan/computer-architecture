package com.Cpu;

import com.CpuOutput.DecodeOutput;

/*
 * 명령어 처리 - 구간 별로 나누기
 * RegDst mux 구현
 * */
public class Decode {


    public DecodeOutput decodeInstruction(boolean IF_IDValid, String binaryInstruction, int pc) {

        if (!IF_IDValid) {
            return DecodeOutput.NONE;
        }

        //변수 할당 부분
        String opcode = checkOpcodeOrFunc(binaryInstruction.substring(0, 6));
        int rs = readTobinaryString(binaryInstruction.substring(6, 11));
        int rt = readTobinaryString(binaryInstruction.substring(11, 16));
        int rd = readTobinaryString(binaryInstruction.substring(16, 21));
        int shamt = readTobinaryString(binaryInstruction.substring(21, 26));
        String func = checkOpcodeOrFunc(binaryInstruction.substring(26, 32));
        String immediate = binaryInstruction.substring(16, 32);
        int signExt = setSignExtImm(binaryInstruction);
        int zeroExt = setZeroExt(binaryInstruction);
        int loadUpperImm = setLoadUpperImm(binaryInstruction);

        ControlSignal controlSignal = new ControlSignal();
        controlSignal.setControlSignal(opcode, func);

        int jumpAddr = jumpAddr(binaryInstruction, pc);
        int branchAddr = branchAddr(binaryInstruction);

        return new DecodeOutput(
                controlSignal,
                opcode,
                rs,
                rt,
                rd,
                shamt,
                func,
                immediate,
                signExt,
                zeroExt,
                loadUpperImm,
                jumpAddr,
                branchAddr
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
        signExtImmBinaryString.append(binaryInstruction, 16, 32);

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
        String zeroExtImmBinaryString = "0000000000000000" +
                binaryInstruction.substring(16, 32);
        return readTobinaryString(zeroExtImmBinaryString);
    }

    //Load Upper Imm 만들기
    //{imm, 16’b0}
    public int setLoadUpperImm(String binaryInstruction) {
        StringBuilder loadUpperImmBinaryString = new StringBuilder();
        loadUpperImmBinaryString.append(binaryInstruction, 16, 32);
        loadUpperImmBinaryString.append("0000000000000000");

        char firstImmeBit = binaryInstruction.charAt(16);
        // 보수 처리 할지 말지 구현
        if (firstImmeBit == '1') {
            return readToNegativebinaryString(loadUpperImmBinaryString.toString());
        } else {
            return readTobinaryString(loadUpperImmBinaryString.toString());
        }
    }


    //jumpAddr 구하기
    //JumpAddr = { PC+4[31:28], address, 2’b0 }
    private int jumpAddr(String binaryInst, int pc) {

            String pcFirst4bits = String.format("%04d", (pc * 4 >> 28) & 0xf);
            String address = binaryInst.substring(6, 32);
            String jumpAddr = pcFirst4bits + address + "00";
            return Integer.parseInt(jumpAddr, 2);
    }

    // BranchAddr 구하기
    // BranchAddr = { 14{immediate[15]}, immediate, 2’b0 }
    private int branchAddr(String binaryInst) {
            String immediate = binaryInst.substring(16, 32);
            if (immediate.charAt(0) == '1') {
                String branchAddr = "11111111111111" + immediate + "00";
                return Integer.parseUnsignedInt(branchAddr, 2);
            } else {
                String branchAddr = "00000000000000" + immediate + "00";
                return Integer.parseInt(branchAddr, 2);
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
