// src/main/java/com/ddis/ddis_hr/organization/command/application/batch/scheduler/AppointmentJobScheduler.java
package com.ddis.ddis_hr.organization.command.application.batch.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
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

    /**
     * 매일 00:05에 “어제(00:00 ~ 오늘 00:00 사이) appointment 데이터를 history로 이동” 작업 실행
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void runAppointmentJobDaily() throws Exception {
        LocalDate today     = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        String start = yesterday
                .atStartOfDay()
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String end   = today
                .atStartOfDay()
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // JobParameters 생성자 시그니처가 Map<String, JobParameter<?>>이므로,
        // Map<String, JobParameter<?>> 형태로 선언해야 합니다.
        Map<String, JobParameter<?>> paramsMap = new HashMap<>();

        // 문자열 파라미터는 identifying=true로 지정 (Job 인스턴스 구분용)
        paramsMap.put("startDate", new JobParameter(start, String.class, true));
        paramsMap.put("endDate",   new JobParameter(end,   String.class, true));
        // 숫자(long) 파라미터는 타입(Long.class)만 지정해도 됩니다.
        paramsMap.put("run.id", new JobParameter(System.currentTimeMillis(), Long.class));

        JobParameters jobParameters = new JobParameters(paramsMap);
        jobLauncher.run(appointmentJob, jobParameters);
    }
}
