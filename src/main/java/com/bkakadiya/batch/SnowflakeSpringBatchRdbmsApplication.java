package com.bkakadiya.batch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
@EnableBatchProcessing
public class SnowflakeSpringBatchRdbmsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SnowflakeSpringBatchRdbmsApplication.class, args);
	}

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;


	@Override
	public void run(String... args) throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("date", UUID.randomUUID().toString())
				.addLong("JobId",System.currentTimeMillis())
				.addLong("time",System.currentTimeMillis()).toJobParameters();

		System.out.println("About to execute the job...");

		JobExecution execution = jobLauncher.run(job, jobParameters);
		System.out.println("STATUS :: "+execution.getStatus());

	}

}
