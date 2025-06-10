package com.ddis.ddis_hr.menu.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FavoriteMenuDTO {
    private Long favoriteMenuId;
    private Long displayOrder;
    private Long menuId;
    private Long employeeId;
}
