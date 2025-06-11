package com.ddis.ddis_hr.eapproval.command.domain.entity;

public enum ApprovalType {
    DRAFT("기안"),
    INTERNAL("내부"),
    COOPERATIVE("협조");

    private final String label;

    ApprovalType(String label) {
        this.label = label;
    }

}