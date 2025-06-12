package com.ddis.ddis_hr.eapproval.command.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DocumentBoxId implements Serializable {

        @Column(name = "employee_id")
        private Long employeeId;

        @Column(name = "doc_id")
        private Long docId;

        // equals / hashCode

}
