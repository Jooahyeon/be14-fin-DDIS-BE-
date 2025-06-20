package com.ddis.ddis_hr.eapproval.query.service;

import com.ddis.ddis_hr.S3Config.service.S3Service;
import com.ddis.ddis_hr.eapproval.command.application.dto.DraftCreateCommandDTO;
import com.ddis.ddis_hr.eapproval.query.dto.*;
import com.ddis.ddis_hr.eapproval.query.mapper.DocumentBoxMapper;
import com.ddis.ddis_hr.eapproval.query.mapper.DraftDocumentMapper;
import com.ddis.ddis_hr.eapproval.query.mapper.DraftMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class DraftDetailServiceImpl implements DraftDetailService {

    private final DraftMapper draftMapper;
    private final ObjectMapper objectMapper;
    private final S3Service s3Service;
    private final DraftDocumentMapper draftDocumentMapper;
    private final DocumentBoxMapper documentBoxMapper;

    // 기안 상세조회
    @Override
    public DraftDetailResponseQueryDTO getDraftDetail(Long docId) {
        // 1) MyBatis 로 DTO + contentDto.refFile (SQL 매핑) 까지 한 번에 가져옴
        // 1) 기본 기안문 정보 및 첨부파일, 결재라인 등 포함된 상세 정보 조회
        DraftDetailResponseQueryDTO dto = draftMapper.selectDraftDetail(docId);

        // 2) rawContent(JSON 또는 HTML) 꺼내기
        String raw = dto.getDocContent();

        // 3) SQL 매핑으로 이미 채워진 contentDto 객체 얻기 - contentDto 객체가 null인 경우 초기화
        ContentQueryDTO content = dto.getContentDto();
        if (content == null) {
            content = new ContentQueryDTO();
            dto.setContentDto(content);
        }

        // 4) JSON 본문(body)만 덮어쓰기
        if (raw != null && raw.trim().startsWith("{")) {
            try {
                ContentQueryDTO parsedJson = objectMapper.readValue(raw, ContentQueryDTO.class);
                content.setBody(parsedJson.getBody());
                // (필요하면 parsedJson.getTitle(), receiver/reference 도 덮어쓰세요)
            } catch (Exception e) {
                log.warn("doc_content JSON 파싱 실패, 본문만 fallback", e);
                content.setBody(raw);
            }
        } else {
            content.setBody(raw);
        }

        // 5) S3 presigned URL 채우기
        if (content.getRefFile() != null) {
            for (FileQueryDTO f : content.getRefFile()) {
                String url = s3Service.generateDownloadUrl(f.getKey(), f.getType());
                f.setKey(url);
            }
        }

        // ✅ 4) [여기!] 수신자·참조자 이름 조회 (document_box 기준)
        List<ReferenceDocDTO> receivers = draftMapper.selectReceiversByDocId(docId);
        List<ReferenceDocDTO> referers = draftMapper.selectReferersByDocId(docId);
        dto.setReceiver(receivers.stream().map(ReferenceDocDTO::getEmployeeName).collect(Collectors.joining(", ")));
        dto.setReferer(referers.stream().map(ReferenceDocDTO::getEmployeeName).collect(Collectors.joining(", ")));


        // 6) 수신자·참조자 문자열 조립v(detail)
        if (content.getReceiver()  != null) dto.setReceiver(String.join(", ", content.getReceiver()));
        if (content.getReferer() != null) dto.setReferer(String.join(", ", content.getReferer()));


        // 7) document_box 테이블에서 협조자 및 참조자 역할 조회 추가 처리
        try {
            Long viewerId = dto.getDrafterId(); // ★ 실제로는 로그인한 사용자 ID로 교체 필요
            List<String> roles = draftMapper.selectRolesByDocIdAndEmployeeId(dto.getDocId(), viewerId);
            if (roles.contains("협조자")) {
                dto.setRole("협조자");
            } else if (roles.contains("참조자")) {
                dto.setRole("참조자");
            } else if (roles.contains("수신자")) {
                dto.setRole("수신자");
            } else if (roles.contains("결재자")) {
                dto.setRole("결재자");
            }
        } catch (Exception e) {
            log.warn("역할 조회 실패", e);
        }
        return dto;
    }






    // 회수
    @Override
    @Transactional
    public void recallDocument(Long docId) {
        log.info("▶▶ recallDocument() 시작: docId={}", docId);
        try {
            int count = draftMapper.countFirstApproverAction(docId);
            if (count == 0) {
                log.warn("회수 불가: 첫 번째 결재자가 이미 결재했거나 없음, docId={}", docId);
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "첫 번째 결재가 완료되어 회수할 수 없습니다."
                );
            }

            try {
                draftMapper.deleteDocumentBoxExceptDrafter(docId);
            } catch (Exception e) {
                log.error("문서함 삭제 실패", e);
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "문서함 삭제 중 오류 발생", e
                );
            }

            try {
                draftMapper.updateDocumentStatusToRecalled(docId);
            } catch (Exception e) {
                log.error("문서 상태 업데이트 실패", e);
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "문서 상태 변경 중 오류 발생", e
                );
            }

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            log.error("recallDocument 처리 중 알 수 없는 예외 발생: docId={}", docId, e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "문서 회수 중 예기치 않은 오류가 발생했습니다.",
                    e
            );
        }

    }


    @Override
    public List<DraftDTO> getMyDrafts(Long employeeId) {

        return draftDocumentMapper.selectDraftsByDrafter(employeeId);
    }

    @Override
    public FindDrafterQueryDTO getfindDrafterInfo(Long employeeId) {
        return null;
    }

}