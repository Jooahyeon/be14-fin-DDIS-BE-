package com.ddis.ddis_hr.payroll.command.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ddis.ddis_hr.payroll.command.application.dto.PayrollMailRequestDTO;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@ContextConfiguration(classes = {PayrollMailServiceImpl.class, PayStubPdfGenerator.class, TemplateEngine.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class PayrollMailServiceImplDiffblueTest {
    @MockitoBean
    private JavaMailSender javaMailSender;

    @Autowired
    private PayrollMailServiceImpl payrollMailServiceImpl;

    /**
     * Test {@link PayrollMailServiceImpl#sendPaystubByEmail(PayrollMailRequestDTO)}.
     * <p>
     * Method under test: {@link PayrollMailServiceImpl#sendPaystubByEmail(PayrollMailRequestDTO)}
     */
    @Test
    @DisplayName("Test sendPaystubByEmail(PayrollMailRequestDTO)")
    @Tag("MaintainedByDiffblue")
    void testSendPaystubByEmail() {
        // Arrange
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.addTemplateResolver(new ClassLoaderTemplateResolver());
        templateEngine.addDialect("$", new StandardDialect());
        PayStubPdfGenerator pdfGenerator = new PayStubPdfGenerator(templateEngine);
        PayrollMailServiceImpl payrollMailServiceImpl = new PayrollMailServiceImpl(new JavaMailSenderImpl(), pdfGenerator);

        PayrollMailRequestDTO dto = new PayrollMailRequestDTO();
        dto.setDeductions(new ArrayList<>());
        dto.setDepartmentName("Department Name");
        dto.setEmployeeEmail("jane.doe@example.org");
        dto.setEmployeeId("42");
        dto.setEmployeeName("Employee Name");
        dto.setHeadName("Head Name");
        dto.setNetSalary(1);
        dto.setPays(new ArrayList<>());
        dto.setRankName("Rank Name");
        dto.setSalaryDate("2020-03-01");
        dto.setTeamName("Team Name");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> payrollMailServiceImpl.sendPaystubByEmail(dto));
    }

    /**
     * Test {@link PayrollMailServiceImpl#sendPaystubByEmail(PayrollMailRequestDTO)}.
     * <p>
     * Method under test: {@link PayrollMailServiceImpl#sendPaystubByEmail(PayrollMailRequestDTO)}
     */
    @Test
    @DisplayName("Test sendPaystubByEmail(PayrollMailRequestDTO)")
    @Tag("MaintainedByDiffblue")
    void testSendPaystubByEmail2() {
        // Arrange
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.addTemplateResolver(new ClassLoaderTemplateResolver());
        PayStubPdfGenerator pdfGenerator = new PayStubPdfGenerator(templateEngine);
        PayrollMailServiceImpl payrollMailServiceImpl = new PayrollMailServiceImpl(new JavaMailSenderImpl(), pdfGenerator);

        PayrollMailRequestDTO dto = new PayrollMailRequestDTO();
        dto.setDeductions(new ArrayList<>());
        dto.setDepartmentName("Department Name");
        dto.setEmployeeEmail("jane.doe@example.org");
        dto.setEmployeeId("42");
        dto.setEmployeeName("Employee Name");
        dto.setHeadName("Head Name");
        dto.setNetSalary(1);
        dto.setPays(new ArrayList<>());
        dto.setRankName("Rank Name");
        dto.setSalaryDate("2020-03-01");
        dto.setTeamName("Team Name");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> payrollMailServiceImpl.sendPaystubByEmail(dto));
    }

    /**
     * Test {@link PayrollMailServiceImpl#sendPaystubByEmail(PayrollMailRequestDTO)}.
     * <ul>
     *   <li>Given {@code null}.</li>
     *   <li>When {@link PayrollMailRequestDTO} (default constructor) EmployeeEmail is {@code null}.</li>
     * </ul>
     * <p>
     * Method under test: {@link PayrollMailServiceImpl#sendPaystubByEmail(PayrollMailRequestDTO)}
     */
    @Test
    @DisplayName("Test sendPaystubByEmail(PayrollMailRequestDTO); given 'null'; when PayrollMailRequestDTO (default constructor) EmployeeEmail is 'null'")
    @Tag("MaintainedByDiffblue")
    void testSendPaystubByEmail_givenNull_whenPayrollMailRequestDTOEmployeeEmailIsNull() {
        // Arrange
        PayrollMailRequestDTO dto = new PayrollMailRequestDTO();
        dto.setDeductions(new ArrayList<>());
        dto.setDepartmentName("Department Name");
        dto.setEmployeeId("42");
        dto.setEmployeeName("Employee Name");
        dto.setHeadName("Head Name");
        dto.setNetSalary(1);
        dto.setPays(new ArrayList<>());
        dto.setRankName("Rank Name");
        dto.setSalaryDate("2020-03-01");
        dto.setTeamName("Team Name");
        dto.setEmployeeEmail(null);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> payrollMailServiceImpl.sendPaystubByEmail(dto));
    }

    /**
     * Test {@link PayrollMailServiceImpl#sendPaystubByEmail(PayrollMailRequestDTO)}.
     * <ul>
     *   <li>Given {@link PayrollMailServiceImpl}.</li>
     * </ul>
     * <p>
     * Method under test: {@link PayrollMailServiceImpl#sendPaystubByEmail(PayrollMailRequestDTO)}
     */
    @Test
    @DisplayName("Test sendPaystubByEmail(PayrollMailRequestDTO); given PayrollMailServiceImpl")
    @Tag("MaintainedByDiffblue")
    void testSendPaystubByEmail_givenPayrollMailServiceImpl() {
        // Arrange
        PayrollMailRequestDTO dto = new PayrollMailRequestDTO();
        dto.setDeductions(new ArrayList<>());
        dto.setDepartmentName("Department Name");
        dto.setEmployeeEmail("jane.doe@example.org");
        dto.setEmployeeId("42");
        dto.setEmployeeName("Employee Name");
        dto.setHeadName("Head Name");
        dto.setNetSalary(1);
        dto.setPays(new ArrayList<>());
        dto.setRankName("Rank Name");
        dto.setSalaryDate("2020-03-01");
        dto.setTeamName("Team Name");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> payrollMailServiceImpl.sendPaystubByEmail(dto));
    }

    /**
     * Test {@link PayrollMailServiceImpl#sendPaystubByEmail(PayrollMailRequestDTO)}.
     * <ul>
     *   <li>Given {@link TemplateEngine} (default constructor) addDialect {@code $} and {@link StandardDialect#StandardDialect()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link PayrollMailServiceImpl#sendPaystubByEmail(PayrollMailRequestDTO)}
     */
    @Test
    @DisplayName("Test sendPaystubByEmail(PayrollMailRequestDTO); given TemplateEngine (default constructor) addDialect '$' and StandardDialect()")
    @Tag("MaintainedByDiffblue")
    void testSendPaystubByEmail_givenTemplateEngineAddDialectDollarSignAndStandardDialect() {
        // Arrange
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.addDialect("$", new StandardDialect());
        PayStubPdfGenerator pdfGenerator = new PayStubPdfGenerator(templateEngine);
        PayrollMailServiceImpl payrollMailServiceImpl = new PayrollMailServiceImpl(new JavaMailSenderImpl(), pdfGenerator);

        PayrollMailRequestDTO dto = new PayrollMailRequestDTO();
        dto.setDeductions(new ArrayList<>());
        dto.setDepartmentName("Department Name");
        dto.setEmployeeEmail("jane.doe@example.org");
        dto.setEmployeeId("42");
        dto.setEmployeeName("Employee Name");
        dto.setHeadName("Head Name");
        dto.setNetSalary(1);
        dto.setPays(new ArrayList<>());
        dto.setRankName("Rank Name");
        dto.setSalaryDate("2020-03-01");
        dto.setTeamName("Team Name");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> payrollMailServiceImpl.sendPaystubByEmail(dto));
    }
}
