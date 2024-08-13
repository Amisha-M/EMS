package com.ems.batch.config;

import com.ems.batch.job.EmployeeItemProcessor;
import com.ems.batch.job.EmployeeItemReaderCsvToH2;
import com.ems.batch.job.EmployeeItemWriterCsvToH2;
import com.ems.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfigCsvToH2 {

    private static final Logger logger = LoggerFactory.getLogger(JobConfigCsvToH2.class);

    @Bean
    @Qualifier("csvToH2Job")
    public Job csvToH2Job(JobRepository jobRepository,
                             JobExecutionListener listener,
                             Step csvToH2Step) {
        return new JobBuilder("CsvToH2Job", jobRepository)
                .listener(listener)
                .start(csvToH2Step)
                .build();
    }

    @Bean
    @Qualifier("csvToH2Step")
    public Step csvToH2Step(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      EmployeeItemReaderCsvToH2 reader,
                      EmployeeItemProcessor processor,
                      EmployeeItemWriterCsvToH2 writer) {
        return new StepBuilder("CsvToH2Step", jobRepository)
                .<Employee, Employee>chunk(5, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

}
