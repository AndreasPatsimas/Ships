package com.papei.pms.ships.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PolygonResponseDto {

    private String id;
    private String name;
    private AreaDto area;
}
