package com.Memory;

import com.Cpu.ControlSignal;

import static com.Memory.Global.dataMemory;

public class Memory {

    int address;
    int writeData;
    ControlSignal controlSignal;
    int aluResult;

    public Memory(int address, int writeData, ControlSignal controlSignal) {
        this.address = address;
        this.writeData = writeData;
        this.controlSignal = controlSignal;

    }

    /*
    * aluResult값 저장
    * */

    public void accessMem() {
        System.out.println("메모리접근시작");
        this.aluResult = address; //address값이 결국 alu값의 결과값이다.
        write();

        System.out.println(writeData);
        System.out.println(controlSignal.memWrite);
        System.out.println(writeData);
    }

    //MemWrite
    void write() {
        if(controlSignal.memWrite) {
            dataMemory[address] = writeData;
        }
    }

}
