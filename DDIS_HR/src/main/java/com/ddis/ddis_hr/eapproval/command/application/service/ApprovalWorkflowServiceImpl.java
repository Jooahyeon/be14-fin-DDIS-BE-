package com.ddis.ddis_hr.eapproval.command.application.service;

import com.ddis.ddis_hr.eapproval.command.domain.entity.ApprovalLine;
import com.ddis.ddis_hr.eapproval.command.domain.entity.DraftDocument;
import com.ddis.ddis_hr.eapproval.command.domain.repository.ApprovalLineRepository;
import com.ddis.ddis_hr.eapproval.command.domain.repository.DraftRepository;
import com.ddis.ddis_hr.notice.command.application.event.NoticeEvent;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ApprovalWorkflowServiceImpl implements ApprovalWorkflowService {
    private final ApprovalLineRepository lineRepo;
    private final DraftRepository draftRepo;
    private final ApplicationEventPublisher publisher;

    public ApprovalWorkflowServiceImpl(ApprovalLineRepository lineRepo,
                                       DraftRepository draftRepo, ApplicationEventPublisher publisher) {
        this.lineRepo = lineRepo;
        this.draftRepo = draftRepo;
        this.publisher = publisher;
    }

    /**
     * 워크플로우 초기화:
     * 1) 첫 번째 결재라인은 '기안' 상태로 고정 (작성자 제출 단계)
     * 2) 두 번째 결재라인만 '미결' 상태로 설정 (실질적 첫 결재자 대기)
     * 3) 세 번째 이후 결재라인은 '대기중' 상태로 설정
     * 4) Draft 상태는 '심사중'으로 설정
     */
    @Override
    @Transactional
    public void initializeWorkflow(DraftDocument draftDocument) {
        // 1) 문서 ID 기준 결재라인 조회
        List<ApprovalLine> lines = lineRepo.findByDocIdOrderByStep(draftDocument.getDocId());

        // 2) 상태 설정: step == 1 (기안자)는 무시
        List<ApprovalLine> initLines = lines.stream()
                .filter(line -> line.getStep() > 1)  // ✅ step 1 무시
                .map(line -> {
                    int step = line.getStep();
                    if (step == 2) {
                        return line.toBuilder()
                                .status("미결") // 첫 결재자
                                .build();
                    } else {
                        return line.toBuilder()
                                .status("대기중") // 이후 단계
                                .build();
                    }
                })
                .toList();

        lineRepo.saveAll(initLines);

        // 3) 문서 상태: '심사중' 처리
        DraftDocument updatedDraftDocument = draftDocument.toBuilder()
                .docStatus("심사중")
                .build();
        draftRepo.save(updatedDraftDocument);
    }

    /**
     * 결재 승인 처리
     * 1) 현재 결재라인이 '미결'인지, approverId 일치 검증
     * 2) 승인 시 status를 '승인'으로 업데이트
     * 3) 다음 단계 라인을 '미결'로 설정 (중간은 '심사중', 마지막은 '결재완료')
     */
    @Override
    @Transactional
    public void approveLine(Long lineId, Long approverId, String opinion) {
        ApprovalLine curr = lineRepo.findById(lineId)
                .orElseThrow(() -> new IllegalArgumentException("결재라인이 없습니다. ID=" + lineId));

        log.debug("▶ approveLine 호출, lineId={}, db.employeeId={}, jwt.employeeId={}, status={}",
                lineId, curr.getEmployeeId(), approverId, curr.getStatus());

        if (!curr.getEmployeeId().equals(approverId) || !"미결".equals(curr.getStatus())) {
            throw new IllegalStateException("권한이 없거나 처리 불가능한 상태입니다.");
        }

        // 승인 처리
        ApprovalLine updatedCurr = curr.toBuilder()
                .status("승인")
                .opinion(opinion)
                .approvedAt(LocalDateTime.now())
                .build();
        lineRepo.save(updatedCurr);

        // 다음 단계 활성화 및 Draft 상태 업데이트
        DraftDocument draftDocument = draftRepo.findById(curr.getDocId())
                .orElseThrow(() -> new IllegalArgumentException("기안문이 없습니다. ID=" + curr.getDocId()));
        List<ApprovalLine> lines = lineRepo.findByDocIdOrderByStep(curr.getDocId());
        int maxStep = lines.stream().mapToInt(ApprovalLine::getStep).max().orElse(curr.getStep());

        if (curr.getStep() < maxStep) {
            // 다음 단계 '미결' 전환
            ApprovalLine next = lines.stream()
                    .filter(l -> l.getStep() == curr.getStep() + 1)
                    .findFirst().get()
                    .toBuilder()
                    .status("미결")
                    .build();
            lineRepo.save(next);

            // Draft 상태는 '심사중'
            draftDocument = draftDocument.toBuilder()
                    .docStatus("심사중")
                    .build();
        } else {
            // 마지막 단계 완료: Draft 상태 '결재완료'
            draftDocument = draftDocument.toBuilder()
                    .docStatus("결재완료")
                    .finalApprovalAt(LocalDateTime.now())
                    .build();

            if (curr.getStep() == maxStep) {
                Long drafterId = draftDocument.getEmployeeId();  // 기안자 ID 꺼내오기
                publisher.publishEvent(new NoticeEvent(
                        this,
                        "결재",                                  // referenceType (문구는 스키마에 맞춰 조정)
                        "결재 완료 안내",                           // title
                        draftDocument.getDocTitle() + " 결재가 완료되었습니다.", // content
                        List.of(drafterId)                         // targets: 기안자만
                ));
            }
        }
        draftRepo.save(draftDocument);
    }

    /**
     * 반려 처리: '미결' 상태에서만 반려 가능
     */
    @Override
    @Transactional
    public void rejectLine(Long lineId, Long approverId, String opinion) {
        ApprovalLine curr = lineRepo.findById(lineId)
                .orElseThrow(() -> new IllegalArgumentException("결재라인이 없습니다. ID=" + lineId));
        if (!curr.getEmployeeId().equals(approverId) || !"미결".equals(curr.getStatus())) {
            throw new IllegalStateException("권한이 없거나 처리 불가능한 상태입니다.");
        }

        ApprovalLine updatedCurr = curr.toBuilder()
                .status("반려")
                .opinion(opinion)
                .approvedAt(LocalDateTime.now())
                .build();
        lineRepo.save(updatedCurr);

        DraftDocument draftDocument = draftRepo.findById(curr.getDocId())
                .orElseThrow(() -> new IllegalArgumentException("기안문이 없습니다. ID=" + curr.getDocId()));
        draftDocument = draftDocument.toBuilder()
                .docStatus("반려")
                .build();
        draftRepo.save(draftDocument);
    }
}

