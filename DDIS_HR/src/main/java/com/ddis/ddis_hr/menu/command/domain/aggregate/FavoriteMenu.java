package com.ddis.ddis_hr.menu.command.domain.aggregate;

import com.ddis.ddis_hr.menu.command.application.dto.FavoriteMenuCommandDTO;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(
        name = "favorite_menu",
        uniqueConstraints = @UniqueConstraint(columnNames = {"employee_id", "menu_id"}) // ← 이 부분 추가
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FavoriteMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_menu_id")
    private Long favoriteMenuId;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    @Column(name = "display_order", nullable = false)
    private Long displayOrder;

    public static FavoriteMenu of(FavoriteMenuCommandDTO dto) {
        FavoriteMenu fm = new FavoriteMenu();
        fm.employeeId = dto.getEmployeeId();
        fm.menuId = dto.getMenuId();
        fm.displayOrder = dto.getDisplayOrder();
        return fm;
    }
}
