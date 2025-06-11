package com.ddis.ddis_hr.eapproval.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//서버에서 처리 결과를 전달 (응답 dto)

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DraftCreateResponseCommandDTO {

        private Long docId;
        private Long approvalLineId;

}
