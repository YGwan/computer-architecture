package com.Cpu;

import com.Logger;

import static com.Main.mux;
import static com.Memory.Global.pc;

public class PC {

    ControlSignal controlSignal;
    String binaryInst;

    public PC(ControlSignal controlSignal) {
        this.controlSignal = controlSignal;
    }

    public void setInstruction(String binaryInst) {
        this.binaryInst = binaryInst;
    }

    //pc 설정 부분
    public void pcUpdate(int rsValue, int aluresult) {

        pc = pc + 1;
        pc = mux(controlSignal.jr, rsValue, pc);
        pc = mux(controlSignal.jump, jumpAddr() / 4, pc);
        pc = mux(bneBeqProcess(aluresult), ((pc * 4 + branchAddr()) / 4), pc);
        pc = mux(controlSignal.jal, jumpAddr() / 4, pc);

        String pcHex;

        if (pc == -1) {
            pcHex = Integer.toHexString(pc);
        } else {
            pcHex = Integer.toHexString(pc * 4);
        }

        Logger.println("Next  PC -> 0x" + pcHex);
    }

    //jumpAddr 구하기
    //JumpAddr = { PC+4[31:28], address, 2’b0 }
    private int jumpAddr() {
        if (controlSignal.jump || controlSignal.jal) {
            String pcFirst4bits = String.format("%04d", (pc * 4 >> 28) & 0xf);
            String address = binaryInst.substring(6, 32);
            String jumpAddr = pcFirst4bits + address + "00";
            return Integer.parseInt(jumpAddr, 2);
        }
        return 0;
    }

    private int branchAddr() {
        if (controlSignal.branch) {
            String immediate = binaryInst.substring(16, 32);
            if (immediate.charAt(0) == '1') {
                String branchAddr = "11111111111111" + immediate + "00";
                return Integer.parseUnsignedInt(branchAddr, 2);
            } else {
                String branchAddr = "00000000000000" + immediate + "00";
                return Integer.parseInt(branchAddr, 2);
            }
        } else return 0;
    }

    //bne 처리
    private boolean bneBeqProcess(int aluResult) {
        boolean boolValue;
        if (aluResult == 1) {
            boolValue = true;
        } else boolValue = false;

        if (boolValue && controlSignal.branch) {
            return true;
        } else {
            return false;
        }
    }
}
