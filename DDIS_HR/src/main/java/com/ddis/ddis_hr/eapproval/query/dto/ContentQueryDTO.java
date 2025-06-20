package com.ddis.ddis_hr.eapproval.query.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ContentQueryDTO {

    private List<FileQueryDTO> refFile;
    private String receiver;     // 수신자 이름
    private String referer;      // ✅ 참조자
    private String body;

}
