package com.ddis.ddis_hr.member.command.domain.repository;

import com.samsung.dieat.member.command.domain.aggregate.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Integer> {
}
