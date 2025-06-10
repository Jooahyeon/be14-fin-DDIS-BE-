package com.ddis.ddis_hr.menu.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MenuDTO {
    private Long menuId;
    private String menuName;
    private Long parentMenuId;
    private String menuPath;
}
