//package com.ddis.ddis_hr.organization.command.application.batch.scheduler;
//
//import org.springframework.batch.core.*;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.*;
//import java.time.*;
//import java.time.format.DateTimeFormatter;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableScheduling
//public class AppointmentJobScheduler {
//    @Autowired
//    private JobLauncher jobLauncher;
//
//    @Autowired
//    private Job appointmentJob;
//
//    /**
//     * 매일 00:05에 “어제(00:00 ~ 오늘 00:00 사이) appointment 데이터를 history로 이동” 작업 실행
//     */
//    @Scheduled(cron = "0 5 0 * * ?")
//    public void runAppointmentJobDaily() throws Exception {
//
//        LocalDate today = LocalDate.now();
//        LocalDate yesterday = today.minusDays(1);
//
//        String start = yesterday.atStartOfDay().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//        String end   = today.atStartOfDay().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//
//        Map<String, JobParameter> paramsMap = new HashMap<>();
//        paramsMap.put("startDate", new JobParameter(start));
//        paramsMap.put("endDate",   new JobParameter(end));
//        // RunIdIncrementer 대체용 유니크 파라미터 (값을 바꿔가며 실행 시키기 위해)
//        paramsMap.put("run.id", new JobParameter(System.currentTimeMillis()));
//
//        JobParameters jobParameters = new JobParameters(paramsMap);
//        jobLauncher.run(appointmentJob, jobParameters);
//    }
//}
