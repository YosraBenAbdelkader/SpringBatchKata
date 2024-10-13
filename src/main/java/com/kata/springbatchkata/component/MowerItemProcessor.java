package com.kata.springbatchkata.component;

import com.kata.springbatchkata.dto.Mower;
import com.kata.springbatchkata.dto.MowerInstruction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;

public class MowerItemProcessor implements ItemProcessor<MowerInstruction, List<Mower>> {
    private static final Logger logger = LogManager.getLogger(MowerItemProcessor.class);

    @Override
    public List<Mower> process(MowerInstruction instruction) throws Exception {
        List<Mower> mowers = new ArrayList<>();

        Mower mower = Mower.builder()
                .x(instruction.getX())
                .y(instruction.getY())
                .orientation(instruction.getOrientation())
                .build();

        logger.debug("Initial position: {} {} {}", mower.getX(), mower.getY(), mower.getOrientation());

        for (char command : instruction.getInstructions().toCharArray()) {
            switch (command) {
                case 'D':
                    mower.setOrientation(turnRight(mower.getOrientation()));
                    break;
                case 'G':
                    mower.setOrientation(turnLeft(mower.getOrientation()));
                    break;
                case 'A':
                    moveMower(mower);
                    break;
            }
        }
        mowers.add(mower);
        logger.debug("Final position: {} {} {}", mower.getX(), mower.getY(), mower.getOrientation());

        return mowers;
    }

    private char turnRight(char currentOrientation) {
        switch (currentOrientation) {
            case 'N':
                return 'E';
            case 'E':
                return 'S';
            case 'S':
                return 'W';
            case 'W':
                return 'N';
            default:
                return currentOrientation;
        }
    }

    private char turnLeft(char currentOrientation) {
        switch (currentOrientation) {
            case 'N':
                return 'W';
            case 'W':
                return 'S';
            case 'S':
                return 'E';
            case 'E':
                return 'N';
            default:
                return currentOrientation;
        }
    }

    private void moveMower(Mower mower) {
        int oldX = mower.getX();
        int oldY = mower.getY();
        switch (mower.getOrientation()) {
            case 'N':
                mower.setY(mower.getY() + 1);
                break;
            case 'E':
                mower.setX(mower.getX() + 1);
                break;
            case 'S':
                mower.setY(mower.getY() - 1);
                break;
            case 'W':
                mower.setX(mower.getX() - 1);
                break;
        }
    }
}