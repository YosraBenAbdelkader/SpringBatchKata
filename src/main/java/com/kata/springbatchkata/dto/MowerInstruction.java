package com.kata.springbatchkata.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor

public class MowerInstruction {
    private int x;
    private int y;
    private char orientation;
    private String instructions;

    public MowerInstruction(int x, int y, char orientation, String instructions) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.instructions = instructions;
    }
}