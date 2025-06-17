package com.ddis.ddis_hr.eapproval.command.application.service;

import com.ddis.ddis_hr.eapproval.command.domain.entity.DraftDocument;

/**
 * 워크플로우 전반(초기화, 승인/반려, 단계 전환)을 담당하는 서비스 인터페이스
 */

public interface ApprovalWorkflowService {
        /**
         * 기안 문서에 대한 결재라인 활성화(초기화)
         */
        void initializeWorkflow(DraftDocument draftDocument);

        /**
         * 지정된 결재라인에 대해 승인 처리
         * @param lineId 결재라인 아이디
         * @param approverId 실제 결재자 사원번호
         * @param opinion 결재의견
         */
        void approveLine(Long lineId, Long approverId, String opinion);

        /**
         * 지정된 결재라인에 대해 반려 처리
         */
        void rejectLine(Long lineId, Long approverId, String opinion);
    }



