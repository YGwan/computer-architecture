package com;

import com.ControlDependence.AlwaysTaken;
import com.ControlDependence.ChooseBranchPrediction;
import com.Cpu.*;
import com.CpuOutput.*;
import com.Latch.EXE_MEM;
import com.Latch.ID_EXE;
import com.Latch.IF_ID;
import com.Latch.MEM_WB;
import com.Memory.Global;

import java.io.IOException;

public class Main extends Global {

    public static void main(String[] args) throws IOException {

        Logger.LOGGING_SIGNAL = false;
        Logger.LOGGING_COUNTER_SIGNAL = false;

        //           Todo : -----------------------------branch Prediction 선택 --------------------------------------
        onBranchPrediction = true; //Todo : false -> stalling, true -> branch prediction 선택
        ChooseBranchPrediction chooseBranchPrediction = new ChooseBranchPrediction();
        chooseBranchPrediction.chooseBP(false ,false, false, true);

        Logger.min = 0;
        Logger.max = -1;

        test("source/simple.bin", 0);
        test("source/simple2.bin", 100);
        test("source/simple3.bin", 5050);
        test("source/simple4.bin", 55);
        test("source/gcd.bin", 1);
        test("source/fib.bin", 55);
        test("source/input4.bin", 85);
    }

    private static void test(String path, int expect) throws IOException {
        int result = ProcessUnit.process(path);
        if (result != expect) {
            System.out.println("failed -> Path : " + path + " expected : " + expect + " real : " + result);
        }
        System.out.println("succeed -> Path : " + path + " expected : " + expect + " real : " + result);
    }

    public static int getNextPc(IF_ID if_id, ID_EXE id_exe,int pc, DecodeOutput decodeOutput) {
        //nextPc값 확인
        int nextPc = pc;
        if (if_id.valid) {
            switch (decodeOutput.controlSignal.inst) {

                case "JUMP":
                case "JAL": {
                    nextPc = decodeOutput.jumpAddr / 4;
                }
                break;

                case "JR": {
                    nextPc = id_exe.inputReadData1;
                }
                break;

                case "BNE":
                case "BEQ": {
                    nextPc = ((if_id.if_idPc + 1) * 4 + decodeOutput.branchAddr) / 4;
                }
                break;
            }
        }
        return nextPc;
    }

    public static void printLogo(boolean fetchValid, int cycleCount, String pcHex,
                                 DecodeOutput decodeOutput, RegisterOutput registerOutput, int finalAluResult, int pc, Register register,
                                 IF_ID if_id, ID_EXE id_exe, EXE_MEM exe_mem, MEM_WB mem_wb,
                                 MemoryFetchOutput memoryFetchOutput, AluOutput aluOutput, PC pcUpdate, Memory memory) {

        memoryFetchOutput.printFetchStage(fetchValid, cycleCount, pcHex, if_id.inputHexInstruction);
        registerOutput.printDecodeStage(if_id.valid, decodeOutput.opcode, decodeOutput.rs, decodeOutput.rt, registerOutput.firstRegisterOutput, registerOutput.secondRegisterOutput);
        aluOutput.printExecutionOutput(id_exe.valid, finalAluResult);
        pcUpdate.pcUpdatePrint(id_exe.valid, pc);
        memory.printExecutionMemoryAccess(exe_mem.valid, exe_mem.controlSignal, exe_mem.finalAluResult, exe_mem.rtValue, exe_mem.instEndPoint);
        register.printExecutionWriteBack(mem_wb.valid, mem_wb.controlSignal, mem_wb.regDst, register.writeData);
        Logger.println();

    }

    public static int mux(boolean signal, int trueVal, int falseVal) {
        if (signal) {
            return trueVal;
        }
        return falseVal;
    }

    public static int forwardMux(int signal, int basic, int exe_memReturnVale, int mem_wbReturnValue) {

        int returnValue;

        switch (signal) {
            case 0:
                returnValue = basic;
                break;
            case 1:
                returnValue = exe_memReturnVale;
                break;
            case 2:
                returnValue = mem_wbReturnValue;
                break;
            default:
                returnValue = 0;
        }
        return returnValue;
    }
}
