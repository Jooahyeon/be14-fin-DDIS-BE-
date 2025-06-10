package com.ddis.ddis_hr.menu.command.application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FavoriteMenuCommandDTO {
    private Long employeeId;
    private Long menuId;
    private Long displayOrder;
}
