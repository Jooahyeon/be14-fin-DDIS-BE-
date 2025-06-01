package com.ddis.ddis_hr.organization.command.application.batch.config;

import com.ddis.ddis_hr.organization.command.application.batch.domain.Appointment;
import com.ddis.ddis_hr.organization.command.application.batch.domain.AppointmentHistory;
import com.ddis.ddis_hr.organization.command.application.batch.listener.JobCompletionNotificationListener;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.repeat.RepeatStatus;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    public BatchConfig(JobBuilderFactory jobBuilderFactory,
                       StepBuilderFactory stepBuilderFactory,
                       DataSource dataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
    }

    private static final String APPOINTMENT_SELECT_SQL =
            "SELECT appointment_id, employee_id, from_head_code, to_head_code, " +
                    "       from_department_code, to_department_code, from_team_code, to_team_code, " +
                    "       from_position_code, to_position_code, from_rank_code, to_rank_code, " +
                    "       from_job_code, to_job_code, appointment_type, appointment_reason, " +
                    "       appointment_created_at, appointment_effective_date, appointment_status, is_applied " +
                    " FROM appointment " +
                    " WHERE appointment_created_at >= :startDate AND appointment_created_at < :endDate";

    private static final String APPOINTMENT_HISTORY_INSERT_SQL =
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
                    "  :appointmentCreatedAt, :appointmentEffectiveDate, :appointmentStatus, :appointmentHistoryCreatedAt)";

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
        queryProvider.setWhereClause("WHERE appointment_created_at >= :startDate AND appointment_created_at < :endDate");
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

    @Bean
    public ItemProcessor<Appointment, AppointmentHistory> appointmentProcessor() {
        return appointment -> {
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
            // 여기에서 LocalDateTime.now()로 바꿔야 TIMESTAMP 타입에 맞을 수 있습니다.
            history.setAppointmentHistoryCreatedAt(LocalDate.now());
            return history;
        };
    }

    @Bean
    public JdbcBatchItemWriter<AppointmentHistory> appointmentWriter() {
        return new JdbcBatchItemWriterBuilder<AppointmentHistory>()
                .dataSource(dataSource)
                .sql(APPOINTMENT_HISTORY_INSERT_SQL)
                .beanMapped()
                .build();
    }

    @Bean
    public Step moveAppointmentToHistoryStep(
            JdbcPagingItemReader<Appointment> appointmentReader,
            ItemProcessor<Appointment, AppointmentHistory> appointmentProcessor,
            JdbcBatchItemWriter<AppointmentHistory> appointmentWriter) {

        return stepBuilderFactory.get("moveAppointmentToHistoryStep")
                .<Appointment, AppointmentHistory>chunk(100)
                .reader(appointmentReader)
                .processor(appointmentProcessor)
                .writer(appointmentWriter)
                .build();
    }

    @Bean
    public Step deleteAppointmentStep() {
        return stepBuilderFactory.get("deleteAppointmentStep")
                .tasklet((contribution, chunkContext) -> {
                    JobParameters jobParameters = chunkContext.getStepContext()
                            .getStepExecution()
                            .getJobParameters();

                    String startDate = jobParameters.getString("startDate");
                    String endDate   = jobParameters.getString("endDate");

                    // JobParameters로 받은 문자열이 ISO-8601 형식(예: 2025-06-01T00:00:00)이라고 가정
                    LocalDateTime start = LocalDateTime.parse(startDate);
                    LocalDateTime end   = LocalDateTime.parse(endDate);

                    String deleteSql =
                            "DELETE FROM appointment " +
                                    "WHERE appointment_created_at >= :startDate AND appointment_created_at < :endDate";
                    Map<String, Object> params = new HashMap<>();
                    params.put("startDate", Timestamp.valueOf(start));
                    params.put("endDate",   Timestamp.valueOf(end));

                    NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
                    jdbcTemplate.update(deleteSql, params);

                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Job appointmentJob(JobCompletionNotificationListener listener,
                              Step moveAppointmentToHistoryStep,
                              Step deleteAppointmentStep) {
        return jobBuilderFactory.get("appointmentJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(moveAppointmentToHistoryStep)
                .next(deleteAppointmentStep)
                .build();
    }
}
