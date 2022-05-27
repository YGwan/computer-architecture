package com.Cpu;

import com.CpuOutput.MemoryOutput;
import com.Logger;
import com.Memory.Global;

import static com.Main.mux;

public class Memory {

    private final int memesize = 0x4000000;
    public int[] dataMemory = new int[memesize];

    //address 값 설정하기

    public int setAddress(ControlSignal controlSignal, int loadUpperImm, int aluResult) {
            if(Global.EXE_MEMValid) {
                return mux(controlSignal.lui, loadUpperImm, aluResult);
            } else  return 0;
    }

    //MemRead
    public MemoryOutput read(int address, ControlSignal controlSignal, boolean instEndPoint) {

        if (instEndPoint) {
            return new MemoryOutput(null, 0);
        } else {
            if (Global.EXE_MEMValid) {

                if (controlSignal.memRead) {
                    Global.InputMEM_WBValid = true;
                    return new MemoryOutput(controlSignal, dataMemory[address]);
                } else {
                    Global.InputMEM_WBValid = true;
                    return new MemoryOutput(controlSignal, 0);
                }
            } else {
                return new MemoryOutput(null, 0);
            }
        }
    }

    //MemWrite
    public void write(int address, int writedata, ControlSignal controlSignal, boolean instEndPoint) {
        if (!instEndPoint) {
            if (Global.EXE_MEMValid) {
                if (controlSignal.memWrite) {
                    dataMemory[address] = writedata;
                }
            }

        }
    }

    public void printExecutionMemoryAccess(ControlSignal controlSignal, int address, int writeData, boolean instEndPoint) {

        if (instEndPoint) {
            Logger.println("MA Stage -> [NOP]");
        } else {
            if (Global.EXE_MEMValid) {
                String index = Integer.toHexString(address);
                if (controlSignal.memRead) {
                    Logger.println("MA Stage -> M[0x%s] = %d\n", index, dataMemory[address]);

                } else if (controlSignal.memWrite) {
                    Logger.println("MA Stage -> M[0x%s] = %d\n", index, writeData);
                } else {
                    Logger.println("MA Stage -> ");
                }
            } else {
                Logger.println("MA Stage -> [NOP]");
            }
        }
    }
}
