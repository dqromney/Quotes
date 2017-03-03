package com.dqr.jobs.eodJob;

import org.springframework.batch.core.JobExecution;

/**
 * Batch Monitoring Notifier interface.
 *
 * Created by dqromney on 3/1/17.
 */
public interface BatchMonitoringNotifier {
  void notify(JobExecution jobExecution);
}
