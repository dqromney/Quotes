package com.dqr.jobs.eodJob;

import com.dqr.factory.Webservice;
import com.dqr.factory.WebserviceFactory;
import com.dqr.utils.Download;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Download Tasklet.
 * <p>
 * Created by dqromney on 3/6/17.
 */
public class DownloadFileTasklet implements Tasklet {

    private Webservice ws = null;

    /**
     * Given the current context in the form of a step contribution, do whatever
     * is necessary to process this unit inside a transaction. Implementations
     * return {@link RepeatStatus#FINISHED} if finished. If not they return
     * {@link RepeatStatus#CONTINUABLE}. On failure throws an exception.
     *
     * @param contribution mutable state to be passed back to update the current
     *                     step execution
     * @param chunkContext attributes shared between invocations but not between
     *                     restarts
     * @return an {@link RepeatStatus} indicating whether processing is
     * continuable.
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        ws = new WebserviceFactory().getWebservice(Webservice.WIKI_EOD_FILE_V3);
        BufferedReader br = new BufferedReader(new InputStreamReader(getWikiFile("PRICES?qopts.export=true")));

        // TODO Make a call to https://www.quandl.com/api/v3/datatables/WIKI/PRICES?qopts.export=true&api_key={{api-key}}

        Download.downloadUsingNIO(ws.getUrl(), "/Users/dqromney/projects/dqr/quotes/src/main/resources/wiki.zip");

        return RepeatStatus.FINISHED;
    }

    private InputStream getWikiFile(String pCommand) throws IOException {
        URL url = new URL(String.format("%1$s%2$s&api_key=%3$s", ws.getUrl(), pCommand, Webservice.API_KEY));
        URLConnection urlConnection = url.openConnection();
        return urlConnection.getInputStream();
    }

}

