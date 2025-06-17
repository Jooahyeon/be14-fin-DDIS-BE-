package com.ddis.ddis_hr.employee.query.service;

import com.ddis.ddis_hr.employee.query.dao.EmployeeMapper;
import com.ddis.ddis_hr.employee.query.dto.EmployeeDTO;
import com.ddis.ddis_hr.employee.query.dto.EmployeeHrDTO;
import com.ddis.ddis_hr.employee.query.dto.EmployeeListDTO;
import com.ddis.ddis_hr.employee.query.dto.EmployeePublicDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeQueryServiceImpl implements EmployeeQueryService {

    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeQueryServiceImpl(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeDTO findByMyId(Long employeeId) {
            return Optional.ofNullable(employeeMapper.findMyInfo(employeeId))
                    .orElseThrow(() ->
                            new EntityNotFoundException("해당 사번의 사원 정보를 찾을 수 없습니다. id=" + employeeId));
    }

    @Override
    public EmployeePublicDTO getPublicById(Long id) {
        EmployeePublicDTO dto = employeeMapper.findPublicById(id);
        if (dto == null) throw new EntityNotFoundException("사원이 없습니다. id=" + id);
        return dto;
    }

    @Override
    public EmployeeHrDTO getHrById(Long id) {
        EmployeeHrDTO dto = employeeMapper.findHrById(id);
        if (dto == null) throw new EntityNotFoundException("사원이 없습니다. id=" + id);
        return dto;
    }

    @Override
    public Object findByIdWithRole(Long id, Collection<? extends GrantedAuthority> authorities) {
        boolean isHr = authorities.stream()
                .anyMatch(a -> "ROLE_HR".equals(a.getAuthority()));
        return isHr
                ? getHrById(id)
                : getPublicById(id);
    }


    @Override
    public List<EmployeeListDTO> getAll() {
        return employeeMapper.findAll();
    }

    @Override
    public List<EmployeeDTO> searchByName(String name) {
        if (name == null || name.isBlank()) {
            return List.of();
            }
            return employeeMapper.findByNameContaining(name.trim());
        }
}

