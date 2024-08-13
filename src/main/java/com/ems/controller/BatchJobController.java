package com.ems.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
public class BatchJobController {

    @Autowired
    @Qualifier("h2ToCsvJob")
    private Job h2ToCsvJob;

    @Autowired
    @Qualifier("csvToH2Job")
    private Job csvToH2Job;

    @Autowired
    @Qualifier("taskletJob")
    private Job taskletJob;

    @Autowired
    private JobLauncher jobLauncher;

    private static final Logger logger = LoggerFactory.getLogger(BatchJobController.class);

    @GetMapping("/startH2ToCsvJob")
    public BatchStatus startH2ToCsvJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(h2ToCsvJob, jobParameters);

        logger.info("H2 to CSV Job {} done...", jobExecution.getJobInstance().getJobName());
        return jobExecution.getStatus();
    }

    @GetMapping("/startCsvToH2Job")
    public BatchStatus startCsvToH2Job() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(csvToH2Job, jobParameters);

        logger.info("CSV to H2 Job {} done...", jobExecution.getJobInstance().getJobName());
        return jobExecution.getStatus();
    }

    @GetMapping("/startTaskletJob")
    public String startJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis()) // Adding timestamp to ensure job runs each time
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(taskletJob, jobParameters);

            logger.info("Job Status : {}", jobExecution.getStatus());
            logger.info("Job completed...");

            return "Job completed with status: " + jobExecution.getStatus();
        } catch (JobExecutionException e) {
            logger.error("Job failed", e);
            return "Job failed: " + e.getMessage();
        }
    }
}

