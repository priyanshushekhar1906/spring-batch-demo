package com.info.demo_batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/batch")
public class JobLauncherController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importUserJob; // This should match the name of your Job bean

    @PostMapping("/start")
    public String launchJob() {
        try {
            // Create unique job parameters to allow multiple runs
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startAt", System.currentTimeMillis()) // unique parameter
                    .toJobParameters();

            jobLauncher.run(importUserJob, jobParameters);

            return "Batch job started successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error starting batch job: " + e.getMessage();
        }
    }

    @GetMapping("/public/greet")
    public String greet() {
            return "This Is Demo Application to implement Spring Batch";
    }
}