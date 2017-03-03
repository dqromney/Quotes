package com.dqr.jobs;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import org.springframework.batch.core.JobExecution;

/**
 * Application Event Monitoring Notifier.
 *
 * Created by dqromney on 3/1/17.
 */
public class ApplicationEventMonitoringNotifier implements ApplicationEventPublisherAware, BatchMonitoringNotifier {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void notify(JobExecution jobExecution) {
        String content = createMessageContent(jobExecution);
        applicationEventPublisher.publishEvent(new SimpleMessageApplicationEvent(this, content));
    }

    /**
     * Set the ApplicationEventPublisher that this object runs in.
     * <p>Invoked after population of normal bean properties but before an init
     * callback like InitializingBean's afterPropertiesSet or a custom init-method.
     * Invoked before ApplicationContextAware's setApplicationContext.
     *
     * @param applicationEventPublisher event publisher to be used by this object
     */
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    private String createMessageContent(JobExecution jobExecution) {
        return String.format("Job Status: %1$s", jobExecution.getExitStatus().toString());
    }

}
