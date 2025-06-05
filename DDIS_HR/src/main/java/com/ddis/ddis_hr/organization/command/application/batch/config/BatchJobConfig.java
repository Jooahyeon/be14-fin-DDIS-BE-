// src/main/java/com/ddis/ddis_hr/organization/command/application/batch/config/BatchJobConfig.java
package com.ddis.ddis_hr.organization.command.application.batch.config;

import com.ddis.ddis_hr.organization.command.application.batch.domain.Appointment;
import com.ddis.ddis_hr.organization.command.application.batch.domain.AppointmentHistory;
import com.ddis.ddis_hr.organization.command.application.batch.listener.JobCompletionNotificationListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class BatchJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;

    public BatchJobConfig(JobRepository jobRepository,
                          PlatformTransactionManager transactionManager,
                          DataSource dataSource) {
        this.jobRepository      = jobRepository;
        this.transactionManager = transactionManager;
        this.dataSource         = dataSource;
    }

    // ------------------------
    // 1) ItemReader 빈 등록
    // ------------------------
    @Bean
    @StepScope
    public JdbcPagingItemReader<Appointment> appointmentReader(
            @Value("#{jobParameters['startDate']}") String startDate,
            @Value("#{jobParameters['endDate']}")   String endDate) {

        Map<String, Object> params = new HashMap<>();
        params.put("startDate", Timestamp.valueOf(LocalDateTime.parse(startDate)));
        params.put("endDate",   Timestamp.valueOf(LocalDateTime.parse(endDate)));

        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause(
                "appointment_id, employee_id, from_head_code, to_head_code, " +
                        "from_department_code, to_department_code, from_team_code, to_team_code, " +
                        "from_position_code, to_position_code, from_rank_code, to_rank_code, " +
                        "from_job_code, to_job_code, appointment_type, appointment_reason, " +
                        "appointment_created_at, appointment_effective_date, appointment_status, is_applied"
        );
        queryProvider.setFromClause("FROM appointment");
        // “승인(appointment_status='승인') 이면서 is_applied=false 이고, 날짜 범위(startDate ≤ created < endDate)인 레코드만 조회”
        queryProvider.setWhereClause(
                "WHERE appointment_status = '승인' " +
                        "  AND is_applied = false"
        );
        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("appointment_id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);

        return new JdbcPagingItemReaderBuilder<Appointment>()
                .name("appointmentReader")
                .dataSource(dataSource)
                .queryProvider(queryProvider)
                .parameterValues(params)
                .rowMapper(new BeanPropertyRowMapper<>(Appointment.class))
                .pageSize(100)
                .build();
    }

    // ------------------------
    // 2) ItemProcessor 빈 등록
    // ------------------------
    @Bean
    public ItemProcessor<Appointment, AppointmentHistory> appointmentProcessor() {
        return appointment -> {
            // 2.1) AppointmentHistory 엔티티 생성
            AppointmentHistory history = new AppointmentHistory();
            history.setAppointmentId(appointment.getAppointmentId());
            history.setEmployeeId(appointment.getEmployeeId());
            history.setFromHeadCode(appointment.getFromHeadCode());
            history.setToHeadCode(appointment.getToHeadCode());
            history.setFromDepartmentCode(appointment.getFromDepartmentCode());
            history.setToDepartmentCode(appointment.getToDepartmentCode());
            history.setFromTeamCode(appointment.getFromTeamCode());
            history.setToTeamCode(appointment.getToTeamCode());
            history.setFromPositionCode(appointment.getFromPositionCode());
            history.setToPositionCode(appointment.getToPositionCode());
            history.setFromRankCode(appointment.getFromRankCode());
            history.setToRankCode(appointment.getToRankCode());
            history.setFromJobCode(appointment.getFromJobCode());
            history.setToJobCode(appointment.getToJobCode());
            history.setAppointmentType(appointment.getAppointmentType());
            history.setAppointmentReason(appointment.getAppointmentReason());
            history.setAppointmentCreatedAt(appointment.getAppointmentCreatedAt());
            history.setAppointmentEffectiveDate(appointment.getAppointmentEffectiveDate());
            history.setAppointmentStatus(appointment.getAppointmentStatus());
            history.setAppointmentHistoryCreatedAt(LocalDate.now());


            // 2.2) 원본 appointment.is_applied = true 로 업데이트
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
            String updateAppointmentSql =
                    "UPDATE appointment " +
                            "   SET is_applied = true " +
                            " WHERE appointment_id = :apptId";
            jdbcTemplate.update(updateAppointmentSql,
                    Map.of("apptId", appointment.getAppointmentId())
            );

            // 2.3) 조직 코드 → ID 변환 후 employee 테이블 갱신
            // 2.3.1) head_code → head_id
            Long headId = jdbcTemplate.queryForObject(
                    "SELECT head_id FROM headquarters WHERE head_code = :hc",
                    Map.of("hc", appointment.getToHeadCode()),
                    Long.class
            );
            // 2.3.2) department_code → department_id
            Long departmentId = jdbcTemplate.queryForObject(
                    "SELECT department_id FROM department WHERE department_code = :dc",
                    Map.of("dc", appointment.getToDepartmentCode()),
                    Long.class
            );
            // 2.3.3) team_code → team_id
            Long teamId = jdbcTemplate.queryForObject(
                    "SELECT team_id FROM team WHERE team_code = :tc",
                    Map.of("tc", appointment.getToTeamCode()),
                    Long.class
            );
            // 2.3.4) position_code → position_id
            Long positionId = jdbcTemplate.queryForObject(
                    "SELECT position_id FROM position WHERE position_code = :pc",
                    Map.of("pc", appointment.getToPositionCode()),
                    Long.class
            );
            // 2.3.5) rank_code → rank_id
            Long rankId = jdbcTemplate.queryForObject(
                    "SELECT rank_id FROM rank WHERE rank_code = :rc",
                    Map.of("rc", appointment.getToRankCode()),
                    Long.class
            );
            // 2.3.6) job_code → job_id
            Long jobId = jdbcTemplate.queryForObject(
                    "SELECT job_id FROM job WHERE job_code = :jc",
                    Map.of("jc", appointment.getToJobCode()),
                    Long.class
            );

            // 2.3.6) 최종 employee 테이블 UPDATE (ID로 반영)
            Map<String, Object> empParams = new HashMap<>();
            empParams.put("hId", headId);
            empParams.put("dId", departmentId);
            empParams.put("tId", teamId);
            empParams.put("pId", positionId);
            empParams.put("rId", rankId);
            empParams.put("jId", jobId);
            empParams.put("eId", appointment.getEmployeeId());

            String updateEmployeeSql =
                    "UPDATE employee " +
                            "   SET head_id       = :hId, " +
                            "       department_id = :dId, " +
                            "       team_id       = :tId, " +
                            "       position_id   = :pId, " +
                            "       rank_id        = :rId " +
                            "       job_id        = :jId " +
                            " WHERE employee_id = :eId";
            jdbcTemplate.update(updateEmployeeSql, empParams);

            return history;
        };
    }

    // ------------------------
    // 3) ItemWriter 빈 등록
    // ------------------------
    @Bean
    public JdbcBatchItemWriter<AppointmentHistory> appointmentWriter() {
        return new JdbcBatchItemWriterBuilder<AppointmentHistory>()
                .dataSource(dataSource)
                .sql(
                        "INSERT INTO appointment_history " +
                                " (appointment_id, employee_id, from_head_code, to_head_code, " +
                                "  from_department_code, to_department_code, from_team_code, to_team_code, " +
                                "  from_position_code, to_position_code, from_rank_code, to_rank_code, " +
                                "  from_job_code, to_job_code, appointment_type, appointment_reason, " +
                                "  appointment_created_at, appointment_effective_date, appointment_status, appointment_history_created_at) " +
                                " VALUES " +
                                " (:appointmentId, :employeeId, :fromHeadCode, :toHeadCode, " +
                                "  :fromDepartmentCode, :toDepartmentCode, :fromTeamCode, :toTeamCode, " +
                                "  :fromPositionCode, :toPositionCode, :fromRankCode, :toRankCode, " +
                                "  :fromJobCode, :toJobCode, :appointmentType, :appointmentReason, " +
                                "  :appointmentCreatedAt, :appointmentEffectiveDate, :appointmentStatus, :appointmentHistoryCreatedAt)"
                )
                .beanMapped()
                .build();
    }

    // ------------------------
    // 4) Step 빈 등록: moveAppointmentToHistoryStep
    // ------------------------
    @Bean
    public Step moveAppointmentToHistoryStep(
            JdbcPagingItemReader<Appointment> appointmentReader,
            ItemProcessor<Appointment, AppointmentHistory> appointmentProcessor,
            JdbcBatchItemWriter<AppointmentHistory> appointmentWriter) {

        return new StepBuilder("moveAppointmentToHistoryStep", jobRepository)
                .<Appointment, AppointmentHistory>chunk(100, transactionManager)
                .reader(appointmentReader)
                .processor(appointmentProcessor)
                .writer(appointmentWriter)
                .build();
    }

    // ------------------------
    // 5) (선택) Step 빈 등록: deleteAppointmentStep
    //    — 만약 처리 후 원본 appointment 레코드를 삭제할 경우
    // ------------------------
    @Bean
    public Step deleteAppointmentStep() {
        return new StepBuilder("deleteAppointmentStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    Map<String, Object> params = new HashMap<>();
                    String startDate = chunkContext.getStepContext()
                            .getStepExecution()
                            .getJobParameters()
                            .getString("startDate");
                    String endDate = chunkContext.getStepContext()
                            .getStepExecution()
                            .getJobParameters()
                            .getString("endDate");

                    LocalDateTime start = LocalDateTime.parse(startDate);
                    LocalDateTime end   = LocalDateTime.parse(endDate);

                    params.put("startDate", Timestamp.valueOf(start));
                    params.put("endDate",   Timestamp.valueOf(end));

                    String deleteSql =
                            "DELETE FROM appointment " +
                                    " WHERE appointment_created_at >= :startDate " +
                                    "   AND appointment_created_at < :endDate " +
                                    "   AND appointment_status = '승인' " +
                                    "   AND is_applied = true";

                    NamedParameterJdbcTemplate jdbcTemplate =
                            new NamedParameterJdbcTemplate(dataSource);
                    jdbcTemplate.update(deleteSql, params);
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    // ------------------------
    // 6) Job 빈 등록: appointmentJob
    // ------------------------
    @Bean
    public Job appointmentJob(JobCompletionNotificationListener listener,
                              Step moveAppointmentToHistoryStep,
                              Step deleteAppointmentStep) {

        return new JobBuilder("appointmentJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(moveAppointmentToHistoryStep)
                // 만약 완료 후 원본 appointment 레코드를 삭제해야 한다면 다음 스텝을 추가:
//                .next(deleteAppointmentStep)
                .build();
    }
}
