package com.ddis.ddis_hr.attendance.query.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LeaveStatusQueryDTO {
    private int totalLeave;
    private double usedLeave;
    private int pendingLeave;
    private double remainingLeave;
    private LocalDate firstPromotionDate;
    private LocalDate secondPromotionDate;
}
