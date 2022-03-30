package com.ca;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Memory {

    private String[] instructions;

    public Memory(String[] instructions) {
        this.instructions = instructions;
    }

    //파일 읽어오기
    public static Memory fileOf(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath),
                    StandardCharsets.UTF_8);
            return new Memory(lines.toArray(new String[lines.size()]));
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public String getInstruction(int address) {
        return instructions[address];
    }

    public int size() {
        return instructions.length;
    }
}
