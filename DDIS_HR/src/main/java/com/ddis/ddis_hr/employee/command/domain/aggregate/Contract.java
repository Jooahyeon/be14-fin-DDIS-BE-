package com.ddis.ddis_hr.employee.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contract")
@Getter @Setter @NoArgsConstructor
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;

    private String contractDescrip;
    private LocalDate requestDate;
    private LocalDate contractDate;
    private LocalDate endDate;
    private Long   employeeId;

    @OneToMany(mappedBy = "contract",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ContractFile> files = new ArrayList<>();

    // helper
    public void addFile(ContractFile file) {
        files.add(file);
        file.setContract(this);
    }
}

