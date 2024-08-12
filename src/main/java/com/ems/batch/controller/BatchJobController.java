package com.ems.batch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
public class BatchJobController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    private static final Logger logger = LoggerFactory.getLogger(BatchJobController.class);

    @GetMapping("/start")
    public String startBatchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startAt", System.currentTimeMillis())
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(job, jobParameters);

            logger.info("Job execution status: {}", jobExecution.getStatus());
            return "Job executed successfully.";
        } catch (Exception e) {
            logger.error("Job execution failed", e);
            return "Job execution failed.";
        }
    }
}
