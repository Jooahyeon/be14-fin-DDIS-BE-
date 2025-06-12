// src/main/java/com/ddis/ddis_hr/organization/command/application/batch/config/BatchJobConfig.java
package com.ddis.ddis_hr.organization.command.application.batch.config;

import com.ddis.ddis_hr.organization.command.application.batch.domain.Appointment;
import com.ddis.ddis_hr.organization.command.application.batch.domain.AppointmentHistory;
import com.ddis.ddis_hr.organization.command.application.batch.listener.AppointmentHistoryNoticeListener;
import com.ddis.ddis_hr.organization.command.application.batch.listener.JobCompletionNotificationListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableBatchProcessing
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

            return history;
        };
    }

    // ------------------------
    // 3) ItemWriter 빈 등록
    // ------------------------
//    @Bean
//    public ItemWriter<AppointmentHistory> appointmentWriter() {
//        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource)
//                .withTableName("appointment_history")
//                .usingGeneratedKeyColumns("appointment_history_id");
//
//        return new ItemWriter<AppointmentHistory>() {
//            @Override
//            public void write(Chunk<? extends AppointmentHistory> chunk) throws Exception {
//                for (AppointmentHistory hist : chunk.getItems()) {
//                    SqlParameterSource params = new BeanPropertySqlParameterSource(hist);
//                    Number key = insert.executeAndReturnKey(params);
//                    hist.setAppointmentHistoryId(key.longValue());
//                }
//            }
//        };

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

    /**
     * 3b) Writer 2: appointment 테이블 is_applied = true 업데이트
     */
    @Bean
    public JdbcBatchItemWriter<AppointmentHistory> appointmentFlagWriter() {
        return new JdbcBatchItemWriterBuilder<AppointmentHistory>()
                .dataSource(dataSource)
                .beanMapped()
                .sql("UPDATE appointment SET is_applied = true WHERE appointment_id = :appointmentId")
                .build();
    }

    /**
     * 3c) Writer 3: employee 테이블에 조직 반영 (code→id 매핑 없이 code 컬럼에 저장)
     * 필요에 따라 서브쿼리로 id 매핑하거나, code 칼럼을 활용하세요.
     */
    @Bean
    public JdbcBatchItemWriter<AppointmentHistory> employeeUpdateWriter() {
        return new JdbcBatchItemWriterBuilder<AppointmentHistory>()
                .dataSource(dataSource)
                .beanMapped()
                .sql(
                    "UPDATE employee " +
                    "   SET head_id        = (SELECT head_id        FROM headquarters WHERE head_code       = :toHeadCode), " +
                    "       department_id  = (SELECT department_id  FROM department   WHERE department_code = :toDepartmentCode), " +
                    "       team_id        = (SELECT team_id        FROM team         WHERE team_code       = :toTeamCode), " +
                    "       position_id  = (SELECT position_id    FROM position     WHERE position_code   = :toPositionCode), " +
                    "       rank_id      = (SELECT rank_id        FROM rank     WHERE rank_code       = :toRankCode), " +
                    "       job_id      = (SELECT job_id        FROM job     WHERE job_code       = :toJobCode) " +

                    " WHERE employee_id = :employeeId" +
                    "   AND DATE(:appointmentEffectiveDate) = CURRENT_DATE()"
                )
                .build();
    }

    /**
     * 4) CompositeItemWriter: 세 개의 Writer를 한 번에 실행
     */
    @Bean
    public CompositeItemWriter<AppointmentHistory> compositeWriter(
            JdbcBatchItemWriter<AppointmentHistory> appointmentWriter,
            JdbcBatchItemWriter<AppointmentHistory> appointmentFlagWriter,
            JdbcBatchItemWriter<AppointmentHistory> employeeUpdateWriter) {
        org.springframework.batch.item.support.CompositeItemWriter<AppointmentHistory> writer =
                new org.springframework.batch.item.support.CompositeItemWriter<>();
        writer.setDelegates(java.util.List.of(
                appointmentWriter,
                appointmentFlagWriter,
                employeeUpdateWriter
        ));
        return writer;
    }

    // ------------------------
    // 4) Step 빈 등록: moveAppointmentToHistoryStep
    // ------------------------
    @Bean
    public Step moveAppointmentToHistoryStep(
            JdbcPagingItemReader<Appointment> appointmentReader,
            ItemProcessor<Appointment, AppointmentHistory> appointmentProcessor,
            CompositeItemWriter<AppointmentHistory> compositeWriter,
            AppointmentHistoryNoticeListener noticeListener) {

        return new StepBuilder("moveAppointmentToHistoryStep", jobRepository)
                .<Appointment, AppointmentHistory>chunk(100, transactionManager)
                .reader(appointmentReader)
                .processor(appointmentProcessor)
                .writer(compositeWriter)
                .listener(noticeListener)
                .build();
    }

    // ------------------------
    // 5) (선택) Step 빈 등록: deleteAppointmentStep
    //    — 만약 처리 후 원본 appointment 레코드를 삭제할 경우
    // ------------------------
//    @Bean
//    public Step deleteAppointmentStep() {
//        return new StepBuilder("deleteAppointmentStep", jobRepository)
//                .tasklet((contribution, chunkContext) -> {
//                    Map<String, Object> params = new HashMap<>();
//                    String startDate = chunkContext.getStepContext()
//                            .getStepExecution()
//                            .getJobParameters()
//                            .getString("startDate");
//                    String endDate = chunkContext.getStepContext()
//                            .getStepExecution()
//                            .getJobParameters()
//                            .getString("endDate");
//
//                    LocalDateTime start = LocalDateTime.parse(startDate);
//                    LocalDateTime end   = LocalDateTime.parse(endDate);
//
//                    params.put("startDate", Timestamp.valueOf(start));
//                    params.put("endDate",   Timestamp.valueOf(end));
//
//                    String deleteSql =
//                            "DELETE FROM appointment " +
//                                    " WHERE appointment_created_at >= :startDate " +
//                                    "   AND appointment_created_at < :endDate " +
//                                    "   AND appointment_status = '승인' " +
//                                    "   AND is_applied = true";
//
//                    NamedParameterJdbcTemplate jdbcTemplate =
//                            new NamedParameterJdbcTemplate(dataSource);
//                    jdbcTemplate.update(deleteSql, params);
//                    return RepeatStatus.FINISHED;
//                }, transactionManager)
//                .build();
//    }

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
