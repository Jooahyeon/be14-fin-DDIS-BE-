package com.ddis.ddis_hr.organization.command.application.batch.listener;

import com.ddis.ddis_hr.employee.command.domain.repository.EmployeeRepository;
import com.ddis.ddis_hr.notice.command.application.event.NoticeEvent;
import com.ddis.ddis_hr.organization.command.application.batch.domain.AppointmentHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppointmentHistoryNoticeListener implements ItemWriteListener<AppointmentHistory> {

    private final ApplicationEventPublisher publisher;
    private final EmployeeRepository employeeRepository;

    // 1) 쓰기 직전에 호출
    @Override
    public void beforeWrite(Chunk<? extends AppointmentHistory> items) {
        log.info("--beforeWrite");
    }


    // 2) 쓰기 성공 후 호출
    @Override
    public void afterWrite(Chunk<? extends AppointmentHistory> items) {
        List<Long> targets = employeeRepository.findAll()
                .stream()
                .map(e -> e.getEmployeeId())
                .collect(Collectors.toList());

        for (AppointmentHistory hist : items) {
            publisher.publishEvent(new NoticeEvent(
                    this,
                    "인사발령",                              // notice_type
                    hist.getAppointmentHistoryId(),         // referenceId
                    "새 인사발령이 등록되었습니다.",            // notice_title
                    hist.getAppointmentReason(),            // notice_content
                    targets                                 // 알림 대상 리스트
            ));
        }
    }

    // 3) 쓰기 중 에러 발생 시 호출
    @Override
    public void onWriteError(Exception exception, Chunk<? extends AppointmentHistory> items) {
        // 로깅이나 예외 처리 로직
    }
}
