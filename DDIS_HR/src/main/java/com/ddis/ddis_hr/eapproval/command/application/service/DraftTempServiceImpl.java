//package com.ddis.ddis_hr.eapproval.command.application.service;
//
//import com.ddis.ddis_hr.eapproval.command.application.dto.DraftTempReqDTO;
//import com.ddis.ddis_hr.eapproval.command.domain.entity.Draft;
//import jakarta.persistence.EntityNotFoundException;
//import jakarta.transaction.Transactional;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//@Service
//public class DraftTempServiceImpl implements DraftTempService {
//    @Override
//    @Transactional
//    public Long saveOrUpdate(DraftTempReqDTO dto) {
//
//        try {
//            /* 1️⃣ 기존 TEMP 문서를 찾거나 없음 → null */
//            Draft origin = null;
//            if (dto.getDocId() != null) {
//                origin = repo.findByDocIdAndDocStatus(dto.getDocId(), "TEMP")
//                        .orElseThrow(() ->
//                                new EntityNotFoundException("해당 임시문서 없음"));
//            } else {
//                origin = repo.findByEmployeeIdAndFormIdAndDocStatus(
//                                dto.getEmployeeId(), dto.getFormId(), "TEMP")
//                        .orElse(null);
//            }
//
//            /* 2️⃣ 새 엔티티 구성 (builder or toBuilder) */
//            Draft entity = (origin == null
//                    ? Draft.builder()              // 신규 INSERT
//                    .docStatus("TEMP")
//                    .createdAt(LocalDateTime.now())
//                    .draftVersion(1)
//                    : origin.toBuilder()           // 기존 → UPDATE
//                    .draftVersion(origin.getDraftVersion() + 1)
//            )
//                    /* ─ 공통 필드 ─ */
//                    .employeeId     (dto.getEmployeeId())
//                    .formId         (dto.getFormId())
//                    .docTitle       (dto.getTitle())
//                    .docContent     (dto.getDocContent())
//                    .preservePeriod (dto.getPreservePeriod())
//                    .draftSavedAt   (LocalDateTime.now())
//                    .approvalJson   (toJson(dto.getApprovalLines()))
//                    .receiversJson  (toJson(dto.getReceivers()))
//                    .ccsJson        (toJson(dto.getCcs()))
//                    .attachmentsJson(toJson(dto.getUploadedFiles()))
//                    /* ★ 여기! 최종 Draft 객체로 변환 */
//                    .build();
//
//            /* 3️⃣ save → INSERT or merge UPDATE */
//            Draft saved = repo.save(entity);
//            log.debug("💾 임시저장 완료 docId={}", saved.getDocId());
//            return saved.getDocId();
//
//        } catch (JsonProcessingException e) {
//            log.error("JSON 직렬화 실패", e);
//            throw new IllegalStateException("임시저장 중 JSON 오류");
//        }
//    }
//
//
//}
