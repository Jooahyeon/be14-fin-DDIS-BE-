package com.ddis.ddis_hr.organization.query.service;

import com.ddis.ddis_hr.organization.query.dao.AppointmentHistoryMapper;
import com.ddis.ddis_hr.organization.query.dto.AppointmentHistoryQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentHistoryQueryService {
    private final AppointmentHistoryMapper historyMapper;

    @Autowired
    public AppointmentHistoryQueryService(AppointmentHistoryMapper historyMapper) {
        this.historyMapper = historyMapper;
    }

    /* 모든 appointment_history를 조회 */
    public List<AppointmentHistoryQueryDTO> getAllHistories() {
        return historyMapper.selectAllHistory();
    }

    /* 특정 사원(employeeId)의 이력만 조회 */
    public List<AppointmentHistoryQueryDTO> getHistoriesByEmployeeId(Long employeeId) {
        return historyMapper.selectByEmployeeId(employeeId);
    }

    /* 특정 appointmentId의 이력만 조회 */
    public List<AppointmentHistoryQueryDTO> getHistoriesByAppointmentId(Long appointmentId) {
        return historyMapper.selectByAppointmentId(appointmentId);
    }

    // 발령 유형 별 조회
    public List<AppointmentHistoryQueryDTO> getByAppointmentType(String appointmentType) {
        return historyMapper.selectByAppointmentType(appointmentType);
    }

    /**
     * 상태가 '승인'인 AppointmentHistory 전체 조회
     */
    public List<AppointmentHistoryQueryDTO> getApprovedHistories() {
        return historyMapper.findByAppointmentStatus("승인");
    }
}
