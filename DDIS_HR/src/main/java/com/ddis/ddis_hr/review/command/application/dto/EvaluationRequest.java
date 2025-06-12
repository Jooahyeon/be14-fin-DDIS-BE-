package com.ddis.ddis_hr.review.command.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluationRequest {
    private Integer reviewerScore;
    private String reviewerContent;
    private Long employeeIdReviewer;
    private String decision;

}
