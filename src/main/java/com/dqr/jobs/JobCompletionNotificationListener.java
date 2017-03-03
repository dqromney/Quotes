package com.dqr.jobs;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * Job Completion Notification Listener.
 *
 * Created by dqromney on 3/1/17.
 */
public class JobCompletionNotificationListener implements JobExecutionListener {
    private BatchMonitoringNotifier monitoringNotifier;

    /**
     * Callback before a job executes.
     *
     * @param jobExecution the current {@link JobExecution}
     */
    @Override
    public void beforeJob(JobExecution jobExecution) {
        // Do nothing
    }

    /**
     * Callback after completion of a job. Called after both both successful and
     * failed executions. To perform logic on a particular status, use
     * "if (jobExecution.getStatus() == BatchStatus.X)".
     *
     * @param jobExecution the current {@link JobExecution}
     */
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            // Notify when job fails
            monitoringNotifier.notify(jobExecution);
        }
        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            // Notify when job fails
            monitoringNotifier.notify(jobExecution);
        }
    }

    public void setMonitoringNotifier(BatchMonitoringNotifier batchMonitoringNotifier) {
        this.monitoringNotifier = batchMonitoringNotifier;
    }
}
