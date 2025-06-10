package com.ddis.ddis_hr.menu.command.domain.repository;

import com.ddis.ddis_hr.menu.command.domain.aggregate.FavoriteMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteMenuRepository extends JpaRepository<FavoriteMenu, Long> {
    void deleteByEmployeeIdAndMenuId(Long employeeId, Long menuId);
    Optional<FavoriteMenu> findByEmployeeIdAndMenuId(Long employeeId, Long menuId);

}
