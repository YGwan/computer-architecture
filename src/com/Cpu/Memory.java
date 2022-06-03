package com.Cpu;

import com.CpuOutput.MemoryOutput;
import com.Logger;
import com.Memory.Global;

import java.util.Locale;

import static com.Main.mux;

public class Memory {

    private final int memesize = 0x1000000;
    public int[] dataMemory = new int[memesize];

    //address 값 설정하기

    //MemRead
    public MemoryOutput read(boolean EXE_MEMValid,int address, ControlSignal controlSignal, boolean instEndPoint) {

        if (instEndPoint) {
            return new MemoryOutput(null, 0);
        } else {
            if (EXE_MEMValid) {
                if (controlSignal.memRead) {
                    return new MemoryOutput(controlSignal, dataMemory[address]);
                } else {
                    return new MemoryOutput(controlSignal, 0);
                }
            } else {
                return new MemoryOutput(null, 0);
            }
        }
    }

    //MemWrite
    public void write(boolean EXE_MEMValid,int address, int writedata, ControlSignal controlSignal, boolean instEndPoint) {
        if (!instEndPoint) {
            if (EXE_MEMValid) {
                if (controlSignal.memWrite) {
                    dataMemory[address] = writedata;
                }
            }
        }
    }

    public void printExecutionMemoryAccess(boolean EXE_MEMValid,ControlSignal controlSignal, int address, int writeData, boolean instEndPoint) {

        if (instEndPoint) {
            Logger.println("MA Stage -> [NOP]");
        } else {
            if (EXE_MEMValid) {
                String index = Integer.toHexString(address).toUpperCase();

                if (controlSignal.memRead) {
                    Logger.println("MA Stage -> M[0x%s] = %d , (0x%s = %s) <LW>\n", index ,dataMemory[address], index ,address);

                } else if (controlSignal.memWrite) {
                    Logger.println("MA Stage -> M[0x%s] = %d , (0x%s = %s) <SW>\n", index ,writeData, index ,address);
                } else {
                    Logger.println("MA Stage -> ");
                }
            } else {
                Logger.println("MA Stage -> [NOP]");
            }
        }
    }
}
