package com.ddis.ddis_hr.organization.command.application.service;

import com.ddis.ddis_hr.organization.command.application.dto.HeadquartersDTO;
import com.ddis.ddis_hr.organization.command.application.dto.HeadquartersRequestDTO;

public interface HeadquartersService {
    HeadquartersDTO createHeadquarters(HeadquartersRequestDTO requestDto);

    HeadquartersDTO updateHeadquarters(Long id, HeadquartersRequestDTO requestDto);

    void deleteHeadquarters(Long id);
}
