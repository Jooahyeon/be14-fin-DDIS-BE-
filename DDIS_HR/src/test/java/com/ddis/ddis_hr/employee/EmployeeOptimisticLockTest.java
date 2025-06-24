package com.ddis.ddis_hr.employee;

import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import com.ddis.ddis_hr.employee.command.domain.repository.EmployeesRepository;
import com.ddis.ddis_hr.organization.command.domain.repository.PositionRepository;
import com.ddis.ddis_hr.organization.command.domain.repository.RankRepository;
import com.ddis.ddis_hr.organization.command.domain.repository.JobsRepository;
import com.ddis.ddis_hr.organization.command.domain.repository.HeadquartersRepository;
import com.ddis.ddis_hr.organization.command.domain.repository.DepartmentRepository;
import com.ddis.ddis_hr.organization.command.domain.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmployeeOptimisticLockTest {

    @Autowired
    private TransactionTemplate txTemplate;

    @Autowired
    private EmployeesRepository repo;

    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private RankRepository rankRepository;
    @Autowired
    private JobsRepository jobsRepository;
    @Autowired
    private HeadquartersRepository headquartersRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Test
    void optimisticLockShouldFailOnConcurrentUpdate() throws InterruptedException {
        // 1) 테스트용 사원 미리 저장 (version = 0)
        Employee emp = Employee.builder()
                .employeeId(20240225569L)
                .employeeName("Test")
                .employeePwd("pwd")
                .employeePhotoName("photo.jpg")
                .employeePhotoUrl("http://example.com/photo.jpg")
                .employeeNation("Korea")
                .employeeGender("남성")
                .employeeBirth(LocalDate.of(1990, 1, 1))
                .employeeResident("900101-1234567")
                .employeeContact("010-1234-5678")
                .employeeEmail("test@example.com")
                .employeeAddress("Seoul")
                .employmentDate(LocalDate.of(2025, 6, 22))
                .retirementDate(null)
                .workType("정규직")
                .bankName("KB")
                .bankDepositor("Test")
                .bankAccount("1234")
                .isDisorder("장애")
                .militaryType("군필")
                .isMarriage("미혼")
                .marriageDate(null)
                .familyCount(1)
                .careerYearCount(0)
                .previousCompany("barelo")
                .finalAcademic("Bachelor")
                .employeeSchool("SNU")
                .employeeDept("CS")
                .graduationYear(2012)
                .isFourInsurances("가입")
                .version(0)
                .position(positionRepository.getReferenceById(1L))
                .rank(rankRepository.getReferenceById(1L))
                .job(jobsRepository.getReferenceById(1L))
                // 여기서부터는 계속 이어집니다.
                .headquarters(headquartersRepository.getReferenceById(1L))
                .department(departmentRepository.getReferenceById(1L))
                .team(teamRepository.getReferenceById(1L))
                .build();
        repo.save(emp);

        // 뮤텍스 역할의 래치와 예외 보관소
        CountDownLatch readLatch   = new CountDownLatch(2);
        CountDownLatch commitLatch = new CountDownLatch(1);
        AtomicReference<Throwable> threadError = new AtomicReference<>();

        // --- A 트랜잭션 (워커 스레드) ---
        Thread t1 = new Thread(() -> {
            try {
                txTemplate.execute(tx -> {
                    Employee e1 = repo.findById(emp.getEmployeeId()).get();
                    readLatch.countDown();         // A 읽음 표시
                    try {
                        readLatch.await();         // 둘 다 읽을 때까지 대기
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(ie);
                    }
                    e1.setEmployeeName("NameA");
                    repo.save(e1);                 // version 0→1
                    commitLatch.countDown();       // 커밋 완료 알림
                    return null;
                });
            } catch (Throwable t) {
                threadError.set(t);
            }
        });
        t1.start();

        // --- B 트랜잭션 (메인 스레드, 충돌 검증) ---
        assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
            txTemplate.execute(tx -> {
                Employee e2 = repo.findById(emp.getEmployeeId()).get();
                readLatch.countDown();        // B 읽음 표시
                try {
                    readLatch.await();        // 둘 다 읽을 때까지 대기
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(ie);
                }
                try {
                    commitLatch.await();      // A 커밋 기다림
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(ie);
                }
                e2.setEmployeeName("NameB");
                return repo.save(e2);        // 충돌 발생
            });
        });

        // 워커 스레드 종료 대기 및 예외 확인
        t1.join();
        if (threadError.get() != null) {
            fail("트랜잭션 A 쪽에서 예외 발생: " + threadError.get());
        }
    }
}
