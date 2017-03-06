package com.dqr.jobs.eodJob;

import com.dqr.factory.Webservice;
import com.dqr.factory.WebserviceFactory;
import com.dqr.model.Data;
import com.dqr.utils.Download;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.Scheduled;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.sql.DataSource;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.Date;
import java.util.zip.ZipInputStream;

/**
 * End of Day Batch Configuration.
 * <p>
 * Created by dqromney on 3/1/17.
 */
public class EodBatchConfiguration {

    private Resource inputResource;
    private String targetDirectory;
    private String targetFile;

    @Autowired
    private SimpleJobLauncher jobLauncher;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    // @Scheduled(cron = "1 53/3 17 * * ?")
    // Every fifteen minutes
    @Scheduled(cron = "0 0/15 * 1/1 * ? *")
    public void perform() throws Exception {
        System.out.println("Job Started at :" + new Date());

        JobParameters param = new JobParametersBuilder().addString("JobID",
                String.valueOf(System.currentTimeMillis())).toJobParameters();

        JobExecution execution = jobLauncher.run(processEodJob(), param);

        System.out.println("Job finished with status :" + execution.getStatus());

    }

    @Bean
    public Job processEodJob() {
        return jobBuilderFactory.get("processEodJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(downloadStep())
                .end()
                .build();
    }

    @Bean
    public Step downloadStep() {
        return stepBuilderFactory.get("downloadStep")
                .tasklet((contribution, chunkContext) -> {
                    Download.downloadUsingNIO(getDatatableBuildDownload().getLink(), "./src/main/resources/wiki.zip");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step decompressStep() {
        return stepBuilderFactory.get("downloadStep")
                .tasklet((contribution, chunkContext) -> {

                    ZipInputStream zis = new ZipInputStream(new BufferedInputStream(inputResource.getInputStream()));

                    File targetDirectoryAsFile = new File(targetDirectory);
                    if (!targetDirectoryAsFile.exists()) {
                        FileUtils.forceMkdir(targetDirectoryAsFile);
                    }
                    File target = new File(targetDirectory, targetFile);
                    BufferedOutputStream dest = null;
                    while(zis.getNextEntry() != null) {
                        if(!target.exists()) {
                            target.createNewFile();
                        }
                        FileOutputStream fos = new FileOutputStream(target);
                        dest = new BufferedOutputStream(fos);
                        IOUtils.copy(zis, dest);
                        dest.flush();
                        dest.close();
                    }
                    zis.close();
                    if(!target.exists()) {
                        throw new IllegalStateException("Could not decompress anything from the archive:");
                    }
                    return RepeatStatus.FINISHED;
                }).build();
    }

//    @Bean
//    public Step eodStep() {
//        return stepBuilderFactory.get("eodStep").chunk(10)
//                .reader(reader())
//                .processor(processor())
//                .writer(writer())
//                .build();
//    }

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

//    @Bean
//    public ItemWriter<SvcReq> writer() {
//        return new DataSvcInvoker();
//    }

    @Bean
    public JobExecutionListener listener() {
        return new JobCompletionNotificationListener();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource mysqlDataSource() throws SQLException {
    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.0.40:3306/quotes");
        dataSource.setUsername("root");
        dataSource.setPassword("iag15501");
        return dataSource;
    }


    /**
     * Get DatatableBuildDownload object.
     *
     * @return {@link DatatableBuildDownload} object
     * @throws IOException
     */
    private DatatableBuildDownload getDatatableBuildDownload() throws IOException {

        InputStream is = getUrlConnection().getInputStream();

        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader jsonReader = factory.createReader(is);
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        is.close();

        DatatableBuildDownload datatableBuildDownload = new DatatableBuildDownload();
        JsonObject topJsonObject = jsonObject.getJsonObject("datatable_bulk_download");

        JsonObject innerInnerJsonObject = topJsonObject.getJsonObject("file");
        datatableBuildDownload.setLink(innerInnerJsonObject.getString("link"));
        datatableBuildDownload.setStatus(innerInnerJsonObject.getString("status"));
        datatableBuildDownload.setDataSnapshotTime(innerInnerJsonObject.getString("data_snapshot_time"));

        innerInnerJsonObject = topJsonObject.getJsonObject("datatable");
        datatableBuildDownload.setLastRefreshedTime(innerInnerJsonObject.getString("last_refreshed_time"));
        return datatableBuildDownload;
    }

    /**
     * Get the URL connection.
     *
     * @return a {@link URLConnection} object
     * @throws IOException
     */
    private URLConnection getUrlConnection() throws IOException {
        Webservice ws = new WebserviceFactory().getWebservice(Webservice.WIKI_EOD_FILE_V3);
        URL url = new URL(String.format("%1$s%2$s&api_key=%3$s", ws.getUrl(), "PRICES?qopts.export=true", Webservice.API_KEY));
        return url.openConnection();
    }

    // ----------------------------------------------------------------
    // Access methods
    // ----------------------------------------------------------------

    public void setInputResource(Resource inputResource) {
        this.inputResource = inputResource;
    }

    public void setTargetDirectory(String targetDirectory) {
        this.targetDirectory = targetDirectory;
    }

    public void setTargetFile(String targetFile) {
        this.targetFile = targetFile;
    }
}
