package com.kata.springbatchkata.listener;

import com.kata.springbatchkata.config.BatchConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Component
public class JobResultListener extends JobExecutionListenerSupport {
    private static final Logger log = LogManager.getLogger(BatchConfiguration.class.getSimpleName());

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Job started at : {}", LocalDateTime.now().atOffset(ZoneOffset.UTC));
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("Job finished at : {}", LocalDateTime.now().atOffset(ZoneOffset.UTC));
        if (jobExecution.getStatus().isUnsuccessful()) {
            System.out.println("Job failed with status: " + jobExecution.getStatus());
        }
    }
}