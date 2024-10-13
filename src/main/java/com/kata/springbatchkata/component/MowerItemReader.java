package com.kata.springbatchkata.component;

import com.kata.springbatchkata.dto.MowerInstruction;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.List;

public class MowerItemReader implements ItemReader<MowerInstruction> {
    private final FlatFileItemReader<String> fileReader;
    private List<MowerInstruction> mowerInstructions = new ArrayList<>();
    private int currentIndex = 0;

    public MowerItemReader(String inputFilePath) {
        fileReader = new FlatFileItemReader<>();
        fileReader.setResource(new ClassPathResource(inputFilePath));
        fileReader.setLinesToSkip(1);
        fileReader.setLineMapper((line, lineNumber) -> line);
        fileReader.open(new ExecutionContext());
        readMowerInstructions();
    }

    private void readMowerInstructions() {
        String line;
        int i = 0;
        try {
            while ((line = fileReader.read()) != null) {
                if (line.matches("\\d+ \\d+ [NSEW]")) {
                    String[] parts = line.split(" ");
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);
                    char orientation = parts[2].charAt(0);

                    mowerInstructions.add(new MowerInstruction(x, y, orientation, ""));
                } else if (line.matches("[GADA]+")) {

                    mowerInstructions.get(i).setInstructions(line);
                    i++;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public MowerInstruction read() throws Exception {
        if (currentIndex < mowerInstructions.size()) {
            return mowerInstructions.get(currentIndex++);
        }
        return null;
    }
}
