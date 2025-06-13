package com.ddis.ddis_hr.board.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardFileDTO {
    private String name;   // file_name
    private String url;    // file_url
    private Integer size;  // file_size
}
