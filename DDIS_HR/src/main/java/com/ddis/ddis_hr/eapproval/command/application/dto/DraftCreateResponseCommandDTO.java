package com.ddis.ddis_hr.eapproval.command.application.dto;

import com.ddis.ddis_hr.eapproval.command.domain.entity.ApprovalLine;
import com.ddis.ddis_hr.eapproval.command.domain.entity.Draft;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

//서버에서 처리 결과를 전달 (응답 dto)

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DraftCreateResponseCommandDTO {

        private Long docId;
        private Long approvalLineId;

        // Draft + ApprovalLine 둘 다 정보가 있는 경우를 처리
        public static DraftCreateResponseCommandDTO fromEntity(Draft draft, ApprovalLine approvalLine) {
                return DraftCreateResponseCommandDTO.builder()
                        .docId(draft.getDocId())
                        .approvalLineId(approvalLine.getApprovalLineId())  // 또는 getApprovalLineId() 등 실제 필드명에 맞게

                        .build();
        }
}
