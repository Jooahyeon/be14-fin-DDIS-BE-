package com.ddis.ddis_hr.member.command.domain.repository;

import com.samsung.dieat.member.command.domain.aggregate.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface  UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserId(String userId);
}
