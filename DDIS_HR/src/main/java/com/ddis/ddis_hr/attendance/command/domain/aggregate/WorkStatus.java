package com.ddis.ddis_hr.attendance.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "work_status")
@Getter
@NoArgsConstructor
public class WorkStatus {

    @Id
    @Column(name = "work_status_id")
    private String id;

    @Column(name = "work_status_name", nullable = false)
    private String name;

}