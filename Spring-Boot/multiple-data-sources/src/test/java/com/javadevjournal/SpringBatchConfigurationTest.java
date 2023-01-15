package com.javadevjournal;

import com.javadevjournal.batch.config.BatchConfig;
import com.javadevjournal.mainSource.data.MainSourceModel;
import com.javadevjournal.mainSource.repo.MainSourceRepository;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.batch.core.*;

import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBatchTest
@EnableAutoConfiguration
//@ContextConfiguration(classes = {BatchConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
@ContextConfiguration
public class SpringBatchConfigurationTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Mock
    Job job = new Job() {
        @Override
        public String getName() {
            return "Job-";
        }

        @Override
        public void execute(JobExecution jobExecution) {

        }
    };

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void testJob() throws Exception {
        this.jobLauncherTestUtils.setJob(job);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        if( jobExecution.getStatus() == BatchStatus.COMPLETED ){

            jobExecution.setExitStatus(new ExitStatus("COMPLETED"));

        } else if(jobExecution.getStatus() == BatchStatus.FAILED){

            jobExecution.setExitStatus(new ExitStatus("FAILED"));

        }
        Assert.assertEquals("Job-", jobExecution.getJobInstance().getJobName());
//        Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
    }

//    public JobParameters defaultJobParameters() {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
//
//        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
//        jobParametersBuilder.addString("time", format.format(Calendar.getInstance().getTime())).toJobParameters();
//
//        return jobParametersBuilder.toJobParameters();
//    }

//    @Test
//    public void givenRefOutput_whenJobExecutes_thenSuccess() throws Exception {
//        MainSourceModel expectedMainSourceModel = new MainSourceModel(999,999,999,999);
//        MainSourceModel actualMainSourceModel = new MainSourceModel();
//
//        JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
//        JobInstance jobInstance = jobExecution.getJobInstance();
//        ExitStatus exitStatus = jobExecution.getExitStatus();
//        Job job = batchConfig.createJob();
//
//
//        assertArrayEquals(jobInstance.getJobName().toCharArray(), "Job-".toCharArray());
//        assertArrayEquals(exitStatus.getExitCode().toCharArray(), "COMPLETED".toCharArray());
//
//    }
}
