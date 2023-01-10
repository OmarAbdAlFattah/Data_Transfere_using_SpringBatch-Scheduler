package com.javadevjournal.batch.config;

import com.javadevjournal.TheProcessor;
import com.javadevjournal.TheReader;
import com.javadevjournal.TheWriter;
import com.javadevjournal.customer.data.CustomerModel;
import com.javadevjournal.product.data.ProductModel;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

//import static java.util.ServiceLoader.fail;

//import javax.sql.DataSource;

//import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    JobRepository jobRepository;
    @Autowired
    PlatformTransactionManager platformTransactionManager;
    @Autowired
    TheReader theReader;
    @Autowired
    TheProcessor theProcessor;
    @Autowired
    TheWriter theWriter;

    @Autowired
    JobLauncher jobLauncher;

    @Bean
    public Job createJob(){


        String jobName = "Job" + System.currentTimeMillis();
        Job job = new JobBuilder(jobName, jobRepository)
                .incrementer(new RunIdIncrementer()).flow(createStep()).end().build();

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time",System.currentTimeMillis()).toJobParameters();

        JobExecution firstExecution = null;
        try {
            firstExecution = jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException e) {
            throw new RuntimeException(e);
        } catch (JobRestartException e) {
            throw new RuntimeException(e);
        } catch (JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }
        jobRepository.update(firstExecution);

        try {
            jobRepository.createJobExecution(jobName, jobParameters);
        }
        catch (JobRestartException e) {
            // expected
        } catch (JobInstanceAlreadyCompleteException e) {
            throw new RuntimeException(e);
        } catch (JobExecutionAlreadyRunningException e) {
            throw new RuntimeException(e);
        }


        return job;
    }

    @Bean
    public Step createStep() {
        TaskletStep step = new StepBuilder("MyStep", jobRepository)
                .<CustomerModel, ProductModel>chunk(1, platformTransactionManager)
                .reader(theReader)
                .processor(theProcessor)
                .writer(theWriter)
                .build();

        return step;
    }
}
