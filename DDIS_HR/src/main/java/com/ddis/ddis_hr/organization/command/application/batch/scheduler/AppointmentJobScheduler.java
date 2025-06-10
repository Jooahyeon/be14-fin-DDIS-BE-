package com.ddis.ddis_hr.organization.command.application.batch.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableScheduling
public class AppointmentJobScheduler {

    private final JobLauncher jobLauncher;
    private final Job appointmentJob;

    @Autowired
    public AppointmentJobScheduler(JobLauncher jobLauncher, Job appointmentJob) {
        this.jobLauncher    = jobLauncher;
        this.appointmentJob = appointmentJob;
    }

    @Scheduled(cron = "0 20 2 * * *", zone = "Asia/Seoul")
    public void runAppointmentJobDaily() throws Exception {
        LocalDate today     = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        String start = yesterday
                .atStartOfDay()
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String end   = today
                .atStartOfDay()
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        JobParameters params = new JobParametersBuilder()
                .addString("startDate", start)
                .addString("endDate",   end)
                .addLong("run.id",      System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(appointmentJob, params);
    }
}
