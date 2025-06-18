package com.ddis.ddis_hr.menu.command.domain.repository;

import com.ddis.ddis_hr.menu.command.domain.aggregate.FavoriteMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteMenuRepository extends JpaRepository<FavoriteMenu, Long> {
    boolean existsByEmployeeIdAndMenuId(Long employeeId, Long menuId);

    void deleteByEmployeeIdAndMenuId(Long employeeId, Long menuId);
    Optional<FavoriteMenu> findByEmployeeIdAndMenuId(Long employeeId, Long menuId);

    @Query("SELECT MAX(f.displayOrder) FROM FavoriteMenu f WHERE f.employeeId = :employeeId")
    Optional<Long> findMaxDisplayOrderByEmployeeId(@Param("employeeId") Long employeeId);
}
