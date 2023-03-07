package com.CpuOutput;

import com.Cpu.ControlSignal;

public class MemoryOutput {

    ControlSignal controlSignal;
    public int memoryCalcResult;

    public MemoryOutput(ControlSignal controlSignal, int memoryCalcResult) {
        this.controlSignal = controlSignal;
        this.memoryCalcResult = memoryCalcResult;
    }

}
