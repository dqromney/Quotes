package com.dqr.jobs.eodJob;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.Resource;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipInputStream;

/**
 * Decompress file tasklet.
 *
 * Created by dqromney on 3/6/17.
 */
public class DecompressFileTasklet implements Tasklet {

    private Resource inputResource;
    private String targetDirectory;
    private String targetFile;

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
    }

    // ----------------------------------------------------------------
    // Accessor methods
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
