package com.ems.batch.config;


import com.ems.batch.job.EmployeeItemProcessor;
import com.ems.batch.job.EmployeeItemReader;
import com.ems.batch.job.EmployeeItemWriter;
import com.ems.batch.job.JobListener;
import com.ems.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
//@EnableAutoConfiguration
//@EnableBatchProcessing
public class JobConfig {

    private static final Logger logger = LoggerFactory.getLogger(JobConfig.class);

    @Bean
    public Job createJob(JobRepository jobRepository,
                         JobListener listener,
                         Step step1) {
        return new JobBuilder("JobExample", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step createStep(JobRepository jobRepository,
                           PlatformTransactionManager transactionManager,
                           EmployeeItemReader reader,
                           EmployeeItemProcessor processor,
                           EmployeeItemWriter writer) {
        return new StepBuilder("JobExample-step1", jobRepository)
                .<Employee, Employee>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

}
