package com.ddis.ddis_hr.menu.command.application.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FavoriteMenuOrderChangeDTO {

    private Long employeeId;
    private List<OrderItem> orders;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class OrderItem {
        private Long menuId;
        private Long displayOrder;
    }
}
