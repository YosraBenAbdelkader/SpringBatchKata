package com.kata.springbatchkata.dto;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@Data
@NoArgsConstructor
public class Mower implements Serializable {

    private int x;
    private int y;
    private char orientation;

    public Mower(int x, int y, char orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }
}
