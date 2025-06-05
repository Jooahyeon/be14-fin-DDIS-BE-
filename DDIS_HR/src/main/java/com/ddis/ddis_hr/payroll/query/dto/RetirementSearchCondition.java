package com.ddis.ddis_hr.payroll.query.dto;
import lombok.*;

import java.time.YearMonth;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RetirementSearchCondition {
    private String retireMonthFrom;
    private String retireMonthTo;
    private String provisionMonthFrom;
    private String provisionMonthTo;
    private String provisionSituation;
    private String keyword;

    public boolean isFilterByRetireDate() {
        return retireMonthFrom != null || retireMonthTo != null;
    }

    public boolean isFilterByProvisionDate() {
        return provisionMonthFrom != null || provisionMonthTo != null;
    }

    public String getFromDate() {
        String month = isFilterByRetireDate() ? retireMonthFrom : provisionMonthFrom;
        return month != null ? month + "-01" : null;
    }

    public String getToDate() {
        String month = isFilterByRetireDate() ? retireMonthTo : provisionMonthTo;
        if (month == null) return null;

        YearMonth ym = YearMonth.parse(month); // 형식: "yyyy-MM"
        return month + "-" + ym.lengthOfMonth(); // 정확한 말일
    }
}
