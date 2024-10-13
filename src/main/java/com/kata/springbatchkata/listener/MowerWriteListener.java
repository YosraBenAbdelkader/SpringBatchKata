package com.kata.springbatchkata.listener;

import com.kata.springbatchkata.dto.Mower;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

public class MowerWriteListener implements ItemWriteListener<Mower> {

    @Override
    public void beforeWrite(List<? extends Mower> items) {
        System.out.println("About to write: " + items.size() + " items");
    }

    @Override
    public void afterWrite(List<? extends Mower> items) {
        System.out.println("Written: " + items.size() + " items");
    }

    @Override
    public void onWriteError(Exception exception, List<? extends Mower> items) {
        System.err.println("Error writing items: " + exception.getMessage());
    }
}