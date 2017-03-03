package com.dqr.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/** End of Day Batch Configuration.
 *
 * Created by dqromney on 3/1/17.
 */
public class EodBatchConfiguration {

    @Autowired
    private SimpleJobLauncher jobLauncher;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Scheduled(cron = "1 53/3 17 * * ?")
    public void perform() throws Exception {
        System.out.println("Job Started at :" + new Date());

        JobParameters param = new JobParametersBuilder().addString("JobID",
                String.valueOf(System.currentTimeMillis())).toJobParameters();

        JobExecution execution = jobLauncher.run(processOrderJob(), param);

        System.out.println("Job finished with status :" + execution.getStatus());

    }

    @Bean
    public Job processOrderJob() {
        return jobBuilderFactory.get("processOrderJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(orderStep()).end().build();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobCompletionNotificationListener();
    }

}
