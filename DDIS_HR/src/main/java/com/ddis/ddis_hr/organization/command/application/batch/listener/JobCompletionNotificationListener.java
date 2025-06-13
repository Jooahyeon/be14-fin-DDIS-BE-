package com.ddis.ddis_hr.organization.command.application.batch.listener;

import org.slf4j.*;
import org.springframework.batch.core.*;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("=== [Appointment Job 시작] ===");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("=== [Appointment Job 정상 종료] ===");
        } else {
            logger.warn("=== [Appointment Job 비정상 종료] === Status: "
                    + jobExecution.getStatus());
        }
    }
}