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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfigH2ToCsv {

    private static final Logger logger = LoggerFactory.getLogger(JobConfigH2ToCsv.class);

    @Bean
    @Qualifier("h2ToCsvJob")
    public Job h2ToCsvJob(JobRepository jobRepository,
                         JobListener listener,
                         Step h2ToCsvStep) {
        return new JobBuilder("H2ToCsvJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(h2ToCsvStep)
                .build();
    }

    @Bean
    @Qualifier("h2ToCsvStep")
    public Step h2ToCsvStep(JobRepository jobRepository,
                           PlatformTransactionManager transactionManager,
                           EmployeeItemReader reader,
                           EmployeeItemProcessor processor,
                           EmployeeItemWriter writer) {
        return new StepBuilder("H2ToCsvStep", jobRepository)
                .<Employee, Employee>chunk(5, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

}
