package com.ddis.ddis_hr.employee.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "disciplinary")
@Getter @Setter @NoArgsConstructor
public class Disciplinary {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long disciplinaryId;

    private String disciplinaryDescrip;
    private LocalDate disciplinaryDate;
    private Long employeeId;


    @OneToMany(mappedBy = "disciplinary",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<DisciplinaryFile> files = new ArrayList<>();


    public void addFile(DisciplinaryFile file) {
        files.add(file);
        file.setDisciplinary(this);
    }
}

