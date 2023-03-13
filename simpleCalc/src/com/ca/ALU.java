package com.ca;

public class ALU {

    private int[] register;

    public ALU(int[] register) {
        this.register = register;
    }

    //함수 구현
    void process(String a, String b, String c) {

        int operand1;
        int operand2;

        //레지스터 or 값 변수 구분
        if (b.indexOf("R") >= 0) {
            operand1 = changeRegisterType(b);
        } else {
            operand1 = changeDataValueType(b);
        }

        if (c.indexOf("R") >= 0) {
            operand2 = changeRegisterType(c);
        } else {
            operand2 = changeDataValueType(c);
        }

        //Opcode에 따라 구분 - switch 문 사용
        switch (a) {
            //Opcode = "+"
            case "+": {
                register[0] = operand1 + operand2;
                System.out.println("R0: " + register[0] + " = " + operand1 + " + " + operand2);
            }
            break;

            //Opcode = "-"
            case "-": {
                register[0] = operand1 - operand2;
                System.out.println("R0: " + register[0] + " = " + operand1 + " - " + operand2);
            }
            break;

            //Opcode = "*"
            case "*": {
                register[0] = operand1 * operand2;
            }
            break;

            //Opcode = "/"
            //0으로 나눌때, 예외처리부분
            case "/": {
                try {
                    register[0] = operand1 / operand2;
                } catch (ArithmeticException e) {
                    System.out.println("0으로는 나눗셈을 할 수 없습니다.");
                }
            }
            break;

            //Opcode = "M"
            case "M": {
                //operand1 수정
                int index = Integer.parseInt(b.substring(1));
                //operand2 값을 operand1에 할당
                register[index] = operand2;
                System.out.println("R" + index + ": " + operand2);
            }
            break;

            //Opcode = "J"
            case "J": {
                register[9] = operand1;
            }
            break;

            //Opcode = "C"
            case "C": {
                //operand1 >= operand2일 경우
                if (operand1 >= operand2) register[0] = 0;
                    //operand1 < operand2일 경우
                else register[0] = 1;
                System.out.println("R0: " + register[0]);
            }
            break;

            //Opcode = "H"
            case "H": {
                register[9] = Integer.MAX_VALUE;
            }
            break;

            //Opcode = "B"
            case "B": {
                //J랑 동일한 형태로 동작하지만 R0값에 따라 동작을 할지 안할지에 대한 제어 추가
                if (register[0] == 1) register[9] = operand1;
                else {
                    //pc 값만 업데이트 해주기
                }
            }
            break;

//            //gcd코드
//            final int gcd(int a, int b) {
//                if (a > b) return gcd(a - b, b);
//                else if (b > a) return gcd(b - a, a);
//                else return a;
//            }

        }

    }

    //operand가 hex값일 경우 처리
    final int changeDataValueType(String x) {
        final int operand;
        //진수에 맞게 값 변환
        operand = Integer.decode(x);
        return operand;
    }

    //operand가 레지스터일 경우 처리
    final int changeRegisterType(String x) {
        final int operand;
        final int index;

        //"R" 제거 부분
        index = Integer.parseInt(x.substring(1));
        //register 값 불러오기
        operand = register[index];
        return operand;
    }

}