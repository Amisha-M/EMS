package com.ems.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobListener implements JobExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(JobListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Job started: {}", jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("Job finished: {}", jobExecution.getJobInstance().getJobName());
    }
}
