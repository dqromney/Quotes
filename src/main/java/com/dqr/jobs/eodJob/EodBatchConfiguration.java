package com.dqr.jobs.eodJob;

import com.dqr.model.Data;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * End of Day Batch Configuration.
 * <p>
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

        JobExecution execution = jobLauncher.run(processEodJob(), param);

        System.out.println("Job finished with status :" + execution.getStatus());

    }

    @Bean
    public Job processEodJob() {
        return jobBuilderFactory.get("processOrderJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(eodStep())
                .end()
                .build();
    }

    @Bean
    public Step eodStep() {
        return stepBuilderFactory.get("eodStep").<Data, SvcReq>chunk(3)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public FlatFileItemReader<Data> reader() {
        FlatFileItemReader<Data> reader = new FlatFileItemReader<Data>();
        reader.setResource(new ClassPathResource("wiki_20170201-sample.csv"));
        reader.setLineMapper(new DefaultLineMapper<Data>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[]{"symbol", "open", "high", "low", "close", "volume", "exDividend", "splitRatio", "adjOpen", "adjHigh", "adjLow", "adjClose", "adjVolume"});
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<Data>() {
                    {
                        setTargetType(Data.class);
                    }
                });
            }
        });
        return reader;
    }

    @Bean
    public DataItemProcessor processor() {
        return new DataItemProcessor();
    }

    @Bean
    public ItemWriter<SvcReq> writer() {
        return new DataSvcInvoker();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobCompletionNotificationListener();
    }

}
