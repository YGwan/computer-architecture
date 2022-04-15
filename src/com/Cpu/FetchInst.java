package com.Cpu;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FetchInst {

    String path;
    private List<String> binaryInstructions = new ArrayList<>();
    private List<String> hexInstructions = new ArrayList<>();

    public FetchInst(String path) {
        this.path = path;
    }

    public List<String> fetch() throws IOException {

        byte[] allInst = new byte[1024];
        int len; // 실제로 읽어온 길이 (바이트 개수)

        FileInputStream in = new FileInputStream(path);

        hexInstructions = new ArrayList<>();

        while ((len = in.read(allInst)) > 0) {
            System.out.println(len);
            for (int i = 0; i < len; i += 4) { // byte[] 버퍼 내용 출력
                hexInstructions.add(String.format("%02X", allInst[i])
                        + String.format("%02X", allInst[i + 1])
                        + String.format("%02X", allInst[i + 2])
                        + String.format("%02X", allInst[i + 3])
                );
            }
            System.out.println(hexInstructions);


            for (int hexIndex = 0; hexIndex < hexInstructions.size(); hexIndex++) {
                String binaryInstruction = "";
                String a = hexInstructions.get(hexIndex);
                for (int bitIndex = 0; bitIndex < 8; bitIndex++) {
                    char singleDigit = a.charAt(bitIndex);
                    String str = String.valueOf(singleDigit);
                    int hexNumber = Integer.parseInt(str, 16);
                    binaryInstruction += String.format("%4s", Integer.toBinaryString(hexNumber)).replace(" ", "0");
                }
                binaryInstructions.add(binaryInstruction);
            }
        }
        return binaryInstructions;
    }

    public void printBitInstruction() {
        for(int instIndex =0; instIndex < binaryInstructions.size(); instIndex++){
            System.out.println(binaryInstructions.get(instIndex));
        }
    }

    public void printhexInstruction() {

        for(int instIndex =0; instIndex < this.hexInstructions.size(); instIndex++){
            System.out.println(this.hexInstructions.get(instIndex));
        }
    }
}

