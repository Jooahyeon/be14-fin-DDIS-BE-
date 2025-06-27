package com.ddis.ddis_hr.employee.command.application.service;

import com.ddis.ddis_hr.employee.command.application.dto.EmployeeEnrollDTO;
import com.ddis.ddis_hr.employee.command.application.dto.EmployeeHrUpdateDTO;
import com.ddis.ddis_hr.employee.command.application.dto.EmployeeUpdateDTO;
import com.ddis.ddis_hr.employee.command.domain.repository.EmployeesRepository;
import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.PositionEntity;
import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.RankEntity;
import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.JobEntity;
import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.HeadquartersEntity;
import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.DepartmentEntity;
import com.ddis.ddis_hr.organization.command.domain.aggregate.entity.TeamEntity;
import com.ddis.ddis_hr.organization.command.domain.repository.PositionRepository;
import com.ddis.ddis_hr.organization.command.domain.repository.RankRepository;
import com.ddis.ddis_hr.organization.command.domain.repository.JobsRepository;
import com.ddis.ddis_hr.organization.command.domain.repository.HeadquartersRepository;
import com.ddis.ddis_hr.organization.command.domain.repository.DepartmentRepository;
import com.ddis.ddis_hr.organization.command.domain.repository.TeamRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private static final int MAX_RETRY = 3;

    private final EmployeesRepository    employeesRepository;
    private final PositionRepository     positionRepository;
    private final RankRepository         rankRepository;
    private final JobsRepository         jobsRepository;
    private final HeadquartersRepository headquartersRepository;
    private final DepartmentRepository   departmentRepository;
    private final TeamRepository         teamRepository;
    private final BCryptPasswordEncoder  bCryptPasswordEncoder;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public EmployeeServiceImpl(
            EmployeesRepository employeesRepository,
            PositionRepository positionRepository,
            RankRepository rankRepository,
            JobsRepository jobsRepository,
            HeadquartersRepository headquartersRepository,
            DepartmentRepository departmentRepository,
            TeamRepository teamRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        this.employeesRepository    = employeesRepository;
        this.positionRepository     = positionRepository;
        this.rankRepository         = rankRepository;
        this.jobsRepository         = jobsRepository;
        this.headquartersRepository = headquartersRepository;
        this.departmentRepository   = departmentRepository;
        this.teamRepository         = teamRepository;
        this.bCryptPasswordEncoder  = bCryptPasswordEncoder;
    }

    @Override
    public Long enrollEmployee(EmployeeEnrollDTO req) {
        int attempt = 0;
        while (true) {
            try {
                return actuallyEnroll(req);
            } catch (ObjectOptimisticLockingFailureException | OptimisticLockException ex) {
                attempt++;
                if (attempt >= MAX_RETRY) {
                    throw ex;  // 최대 재시도 초과 시 예외 상위로 전달
                }
                // (선택) Thread.sleep(50);
            }
        }
    }

    private Long actuallyEnroll(EmployeeEnrollDTO req) {
        // 1) 연관 엔티티 프록시 조회
        PositionEntity     position     = positionRepository.getReferenceById(req.getPositionId());
        RankEntity         rank         = rankRepository.getReferenceById(req.getRankId());
        JobEntity          job          = jobsRepository.getReferenceById(req.getJobId());
        HeadquartersEntity headquarters = headquartersRepository.getReferenceById(req.getHeadId());
        DepartmentEntity   department   = departmentRepository.getReferenceById(req.getDepartmentId());
        TeamEntity         team         = teamRepository.getReferenceById(req.getTeamId());

        // 2) 순번 계산 (YYYYMMDD + 3-digit jobId + 3-digit seq)
        String datePart = req.getEmploymentDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String jobPart  = String.format("%03d", req.getJobId());
        String prefix   = datePart + jobPart;

        Long maxId = employeesRepository.findMaxIdByPrefixForUpdate(prefix);
        int nextSeq = 1;
        if (maxId != null) {
            String maxSeqStr = String.valueOf(maxId).substring(prefix.length());
            nextSeq = Math.min(Integer.parseInt(maxSeqStr) + 1, 999);
        }
        String seqPart     = String.format("%03d", nextSeq);
        Long newEmployeeId = Long.parseLong(prefix + seqPart);

        // 3) 비밀번호 처리: 기본값 “1234” + Bcrypt 암호화
        String rawPwd = req.getEmployeePwd();
        if (rawPwd == null || rawPwd.isBlank()) {
            rawPwd = "1234";
        }
        String encodedPwd = bCryptPasswordEncoder.encode(rawPwd);

        // 4) DTO → Entity 빌드
        Employee employee = Employee.builder()
                .employeeId       (newEmployeeId)
                .employeeName     (req.getEmployeeName())
                .employeePwd      (encodedPwd)
                .employeePhotoName(req.getEmployeePhotoName())
                .employeePhotoUrl (req.getEmployeePhotoUrl())
                .employeeNation   (req.getEmployeeNation())
                .employeeGender   (req.getEmployeeGender())
                .employeeBirth    (req.getEmployeeBirth())
                .employeeResident (req.getEmployeeResident())
                .employeeContact  (req.getEmployeeContact())
                .employeeEmail    (req.getEmployeeEmail())
                .employeeAddress  (req.getEmployeeAddress())
                .employmentDate   (req.getEmploymentDate())
                .retirementDate   (req.getRetirementDate())
                .workType         (req.getWorkType())
                .bankName         (req.getBankName())
                .bankDepositor    (req.getBankDepositor())
                .bankAccount      (req.getBankAccount())
                .isDisorder       (req.getIsDisorder())
                .militaryType     (req.getMilitaryType())
                .isMarriage       (req.getIsMarriage())
                .marriageDate     (req.getMarriageDate())
                .familyCount      (req.getFamilyCount())
                .careerYearCount  (req.getCareerYearCount())
                .previousCompany  (req.getPreviousCompany())
                .finalAcademic    (req.getFinalAcademic())
                .employeeSchool   (req.getEmployeeSchool())
                .employeeDept     (req.getEmployeeDept())
                .graduationYear   (req.getGraduationYear())
                .isFourInsurances (req.getIsFourInsurances())
                .position         (position)
                .rank             (rank)
                .job              (job)
                .headquarters     (headquarters)
                .department       (department)
                .team             (team)
                .build();

        // 5) 저장 및 PK 반환
        em.persist(employee);
        return employee.getEmployeeId();
    }

    @Override
    public void updateEmployee(Long employeeId, EmployeeUpdateDTO dto) {
        int attempt = 0;
        while (true) {
            try {
                actuallyUpdate(employeeId, dto);
                return;
            } catch (ObjectOptimisticLockingFailureException | OptimisticLockException ex) {
                attempt++;
                if (attempt >= MAX_RETRY) {
                    throw ex;
                }
            }
        }
    }

    private void actuallyUpdate(Long employeeId, EmployeeUpdateDTO dto) {
        Employee employee = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("등록된 사원이 없습니다: " + employeeId));

        employee.setEmployeePhotoName(dto.getEmployeePhotoName());
        employee.setEmployeePhotoUrl(dto.getEmployeePhotoUrl());
        employee.setEmployeeBirth(dto.getEmployeeBirth());
        employee.setEmployeeContact(dto.getEmployeeContact());
        employee.setEmployeeEmail(dto.getEmployeeEmail());
        employee.setEmployeeAddress(dto.getEmployeeAddress());
        employee.setBankName(dto.getBankName());
        employee.setBankDepositor(dto.getBankDepositor());
        employee.setBankAccount(dto.getBankAccount());

        employeesRepository.save(employee);
    }

    @Override
    public void hrUpdateEmployee(Long employeeId, EmployeeHrUpdateDTO dto) {
        int attempt = 0;
        while (true) {
            try {
                actuallyHrUpdate(employeeId, dto);
                return;
            } catch (ObjectOptimisticLockingFailureException | OptimisticLockException ex) {
                attempt++;
                if (attempt >= MAX_RETRY) {
                    throw ex;
                }
            }
        }
    }

    private void actuallyHrUpdate(Long employeeId, EmployeeHrUpdateDTO dto) {
        Employee e = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("사원이 없습니다: " + employeeId));

        e.setEmployeeName     (dto.getEmployeeName());
        e.setEmployeePhotoName(dto.getEmployeePhotoName());
        e.setEmployeePhotoUrl (dto.getEmployeePhotoUrl());
        e.setEmployeeNation   (dto.getEmployeeNation());
        e.setEmployeeGender   (dto.getEmployeeGender());
        e.setEmployeeBirth    (dto.getEmployeeBirth());
        e.setEmployeeResident (dto.getEmployeeResident());
        e.setEmployeeContact  (dto.getEmployeeContact());
        e.setEmployeeEmail    (dto.getEmployeeEmail());
        e.setEmployeeAddress  (dto.getEmployeeAddress());
        e.setEmploymentDate   (dto.getEmploymentDate());
        e.setRetirementDate   (dto.getRetirementDate());
        e.setWorkType         (dto.getWorkType());
        e.setBankName         (dto.getBankName());
        e.setBankDepositor    (dto.getBankDepositor());
        e.setBankAccount      (dto.getBankAccount());
        e.setIsDisorder       (dto.getIsDisorder());
        e.setMilitaryType     (dto.getMilitaryType());
        e.setIsMarriage       (dto.getIsMarriage());
        e.setMarriageDate     (dto.getMarriageDate());
        e.setFamilyCount      (dto.getFamilyCount());
        e.setCareerYearCount  (dto.getCareerYearCount());
        e.setPreviousCompany  (dto.getPreviousCompany());
        e.setFinalAcademic    (dto.getFinalAcademic());
        e.setEmployeeSchool   (dto.getEmployeeSchool());
        e.setEmployeeDept     (dto.getEmployeeDept());
        e.setGraduationYear   (dto.getGraduationYear());
        e.setIsFourInsurances (dto.getIsFourInsurances());

        PositionEntity     pos = positionRepository.getReferenceById(dto.getPositionId());
        RankEntity         rk  = rankRepository.getReferenceById(dto.getRankId());
        JobEntity          jb  = jobsRepository.getReferenceById(dto.getJobId());
        HeadquartersEntity hq  = headquartersRepository.getReferenceById(dto.getHeadId());
        DepartmentEntity   dp  = departmentRepository.getReferenceById(dto.getDepartmentId());
        TeamEntity         tm  = teamRepository.getReferenceById(dto.getTeamId());
        e.setPosition(pos);
        e.setRank(rk);
        e.setJob(jb);
        e.setHeadquarters(hq);
        e.setDepartment(dp);
        e.setTeam(tm);

        employeesRepository.save(e);
    }

//    @Override
//    public void deleteEmployee(Long employeeId) {
//        if (!employeesRepository.existsById(employeeId)) {
//            throw new EntityNotFoundException("삭제할 사원이 없습니다: " + employeeId);
//        }
//        employeesRepository.deleteById(employeeId);
//    }

}
