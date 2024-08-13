package com.ems.batch.config;

import com.ems.batch.tasklet.ProcessorTasklet;
import com.ems.batch.tasklet.ReaderTasklet;
import com.ems.batch.tasklet.WriterTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchJobConfigTasklet {

    @Bean
    @Qualifier("taskletJob")
    public Job taskletJob(JobRepository jobRepository, Step readerStep, Step processorStep, Step writerStep) {
        return new JobBuilder("taskletJob", jobRepository)
                .start(readerStep)
                .next(processorStep)
                .next(writerStep)
                .build();
    }

    @Bean
    @Qualifier("readerStep")
    public Step readerStep(JobRepository jobRepository, ReaderTasklet readerTasklet) {
        return new StepBuilder("readerStep", jobRepository)
                .tasklet(readerTasklet, new ResourcelessTransactionManager())
                .build();
    }

    @Bean
    @Qualifier("processorStep")
    public Step processorStep(JobRepository jobRepository, ProcessorTasklet processorTasklet) {
        return new StepBuilder("processorStep", jobRepository)
                .tasklet(processorTasklet, new ResourcelessTransactionManager())
                .build();
    }

    @Bean
    @Qualifier("writerStep")
    public Step writerStep(JobRepository jobRepository, WriterTasklet writerTasklet) {
        return new StepBuilder("writerStep", jobRepository)
                .tasklet(writerTasklet, new ResourcelessTransactionManager())
                .build();
    }
}
