//package com.ddis.ddis_hr.eapproval.query.controller;
//
//import com.ddis.ddis_hr.eapproval.query.dto.ApprovalStepQueryDTO;
//import com.ddis.ddis_hr.eapproval.query.service.ApprovalStepQueryService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/approval-rule")
//public class ApprovalStepQueryController {
//
//    private final ApprovalStepQueryService approvalStepQueryService;
//
//    public ApprovalStepQueryController(ApprovalStepQueryService approvalStepQueryService) {
//        this.approvalStepQueryService = approvalStepQueryService;
//    }
//
//    @GetMapping
//    public List<ApprovalStepQueryDTO> getSteps(@RequestParam Long positionId) {
//        return approvalStepQueryService.findSteps(positionId);
//    }
//}
