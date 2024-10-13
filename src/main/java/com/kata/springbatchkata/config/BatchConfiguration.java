package com.kata.springbatchkata.config;

import com.kata.springbatchkata.component.*;
import com.kata.springbatchkata.dto.Mower;
import com.kata.springbatchkata.dto.MowerInstruction;
import com.kata.springbatchkata.listener.JobResultListener;
import com.kata.springbatchkata.listener.MowerWriteListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;


import java.util.List;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackages = "com.kata.springbatchkata")
public class BatchConfiguration {
    private static final Logger logger = LogManager.getLogger(BatchConfiguration.class.getSimpleName());

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobResultListener jobResultListener;

    @Autowired
    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, JobResultListener jobResultListener) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobResultListener = jobResultListener;
    }

    @Bean
    public ItemProcessor<MowerInstruction, List<Mower>> processor() {
        return new MowerItemProcessor();
    }

    @Bean
    public ItemReader<MowerInstruction> reader() {
        return new MowerItemReader("input.txt");
    }


    @Bean
    @StepScope
    public MowerItemWriter writer() {
        return new MowerItemWriter(new FileSystemResource("src/main/resources/output.txt"));
    }

    @Bean
    public Step step1() {
        logger.debug("debug step1...");
        return stepBuilderFactory.get("step1")
                .<MowerInstruction, List<Mower>>chunk(1)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .listener(new MowerWriteListener())
                .build();
    }

    @Bean
    public Job processMowerJob(JobResultListener listener, Step step1) {
        logger.debug("debug Job...");
        return jobBuilderFactory.get("processMowerJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }
}