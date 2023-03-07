package com.ca;

public class Main {
    public static void main(String args[]) {

        int[] register = new int[10];
        ALU calculator = new ALU(register);
        // 레지스터 변수 사이에 메모리 연속성 관계 부여

        //변수 선언
        String filePath = "input.txt";

        //file 읽어오기
        Memory memory = Memory.fileOf(filePath);


        while (register[9] < memory.size()) {

            //pc변수하고 레지스터 변수와 연결, pc변수 업데이트
            int pc = register[9]++;

            //" "을 기준으로 문자 split하기
            String[] text = memory.getInstruction(pc).split(" ");
            int textLength = text.length;

            //operand 개수에 따라 구분 - 배열의 길이에 따라 구분
            if (textLength == 3) {
                calculator.process(text[0], text[1], text[2]);
            } else if (textLength == 2) {
                calculator.process(text[0], text[1], "0");
            } else {
                calculator.process(text[0], "0", "0");
            }
        }

        //리턴 값 저장
        int v0 = register[8];
        System.out.println("GCD는 " + v0 + " 입니다.");
    }
}
