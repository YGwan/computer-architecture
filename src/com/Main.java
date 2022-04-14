package com;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        String filename = "source/simple.bin";
        byte[] insts = new byte[1024];
        int len; // 실제로 읽어온 길이 (바이트 개수)
        int counter = 0; //총 명령어 수 출력 변수
        //System.out.format("[%d 바이트를 읽어서 출력]", counter);

        FileInputStream in = new FileInputStream(filename);

        List<String> inst = new ArrayList<>();
        while ((len = in.read(insts)) > 0) {
            for (int i = 0; i < len / 4; i += 4) { // byte[] 버퍼 내용 출력
                String temp = "";
                temp += String.format("%02X", insts[i]);
                temp += String.format("%02X", insts[i + 1]);
                temp += String.format("%02X", insts[i + 2]);
                temp += String.format("%02X", insts[i + 3]);
                inst.add(temp);
                counter++;
            }
        }
        //0010 0111 1011 1101 1000 0000 0001 0000
        System.out.println(inst.get(1).toString());


        String[] ins_to_bit;


        for(int k =0; k<inst.size(); k++) {
            String instruction ="";
            String a = inst.get(k).toString();
            for (int i = 0; i < 8; i++) {

                char ins_one_word = a.charAt(i);
                //System.out.println(ins_one_word);
                String str = String.valueOf(ins_one_word);
                int num = Integer.parseInt(str, 16);
                //System.out.println(num);
                String word = String.format("%4s", Integer.toBinaryString(num));
                word = word.replace(" ", "0");
                instruction += word;
            }
            System.out.println(instruction);
        }


    }
}

//27BD8010
//0010011110010101001000000000010000