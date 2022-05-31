package com.Cpu;

import com.Memory.Global;
import com.CpuOutput.MemoryFetchOutput;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * 파일을 읽어와 String binary String과 hexString으로
 * instruction을 가져오는 부분
 *
 * Load 하는 부분
 * */
public class MemoryFetch {

    private final List<String> binaryInstructions;
    private final List<String> hexInstructions;

    public MemoryFetch(String path) throws IOException {
        this.hexInstructions = load(path);
        this.binaryInstructions = hexToBinary(hexInstructions);
    }

    private static List<String> load(String path) throws IOException {
        byte[] allInst = new byte[1024];
        int len; // 실제로 읽어온 길이 (바이트 개수)

        FileInputStream in = new FileInputStream(path);
        List<String> hexInstructions = new ArrayList<>();

        while ((len = in.read(allInst)) > 0) {
            for (int i = 0; i < len; i += 4) { // byte[] 버퍼 내용 출력
                hexInstructions.add(String.format("%02X", allInst[i])
                        + String.format("%02X", allInst[i + 1])
                        + String.format("%02X", allInst[i + 2])
                        + String.format("%02X", allInst[i + 3])
                );
            }
        }
        return hexInstructions;
    }

    private static List<String> hexToBinary(List<String> hexInstructions) {
        List<String> binaryInstructions = new ArrayList<>();
        for (String instruction : hexInstructions) {
            StringBuilder binaryInstruction = new StringBuilder();
            for (int bitIndex = 0; bitIndex < 8; bitIndex++) {
                char singleDigit = instruction.charAt(bitIndex);
                String str = String.valueOf(singleDigit);
                int hexNumber = Integer.parseInt(str, 16);
                binaryInstruction.append(String.format("%4s", Integer.toBinaryString(hexNumber)).
                        replace(" ","0"));
            }
            binaryInstructions.add(binaryInstruction.toString());
        }
        return binaryInstructions;
    }



    public MemoryFetchOutput fetch(boolean fetchValid, int pc) {

        if(fetchValid) {
            return new MemoryFetchOutput(
                    binaryInstructions.get(pc),
                    hexInstructions.get(pc),
                    pc + 1
            );
        } else {
            return new MemoryFetchOutput(
                    null,
                    null,
                    pc+1
            );
        }
    }



    public String printHexInst(int pc) {
        return hexInstructions.get(pc);
    }



//
//    public int size() {
//        return binaryInstructions.size();
//    }
//
//    public void printBitInstruction() {
//        for (String binaryInstruction : binaryInstructions) {
//            System.out.println(binaryInstruction);
//        }
//    }
//
//    public void printHexInstruction() {
//        for (String hexInstruction : this.hexInstructions) {
//            System.out.println(hexInstruction);
//        }
//    }
}

