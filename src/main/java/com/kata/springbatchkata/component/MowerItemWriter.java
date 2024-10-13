package com.kata.springbatchkata.component;

import com.kata.springbatchkata.dto.Mower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemWriter;

import org.springframework.core.io.Resource;


import java.io.*;

import java.util.List;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MowerItemWriter implements ItemWriter<List<Mower>> {
    private static final Logger logger = LogManager.getLogger(MowerItemWriter.class);

    private final Resource outputResource;

    public MowerItemWriter(Resource outputResource) {
        this.outputResource = outputResource;
        deleteAndCreateFile();
    }


    @Override
    public void write(List<? extends List<Mower>> items) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputResource.getFile(), true))) {
            for (List<Mower> mowers : items) {
                for (Mower mower : mowers) {
                    String result = mower.getX() + " " + mower.getY() + " " + mower.getOrientation();
                    writer.write(result);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            logger.error("Failed to write to output file", e);
            throw new RuntimeException("Failed to write to output file", e);
        }
    }

    private void deleteAndCreateFile() {
        File outputFile = null;
        try {
            outputFile = outputResource.getFile();
            if (outputFile.exists()) {
                if (!outputFile.delete()) {
                    logger.warn("Failed to delete existing output file: " + outputFile.getAbsolutePath());
                }
            }
            if (outputFile.createNewFile()) {
                logger.info("Output file created: " + outputFile.getAbsolutePath());
            } else {
                logger.error("Failed to create output file: " + outputFile.getAbsolutePath());
            }
        } catch (IOException e) {
            logger.error("Error while handling output file", e);
            throw new RuntimeException("Error while handling output file", e);
        }
    }
}

