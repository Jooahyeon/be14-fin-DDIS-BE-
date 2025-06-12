package com.ddis.ddis_hr.notice.command.application.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class NoticeEvent extends ApplicationEvent {

    /**
     * referenceType: '인사발령', '리마인더', '결재' 등 DDL의 notice_type 값과 매핑
     * referenceId:   예) appointment_history_id 등 연관 리소스 PK
     * title:         DDL의 notice_title
     * content:       DDL의 notice_content
     * targets:       알림을 받을 employee_id 리스트
     */
    private final String referenceType;
    private final Long referenceId;
    private final String title;
    private final String content;
    private final List<Long> targets;

    public NoticeEvent(Object source,
                             String referenceType,
                             Long referenceId,
                             String title,
                             String content,
                             List<Long> targets) {
        super(source);
        this.referenceType = referenceType;
        this.referenceId   = referenceId;
        this.title         = title;
        this.content       = content;
        this.targets       = targets;
    }
}
