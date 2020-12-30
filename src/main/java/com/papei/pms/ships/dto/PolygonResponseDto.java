package com.papei.pms.ships.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PolygonResponseDto {

    private String id;
    private String name;
    private AreaDto area;
    private LocalDateTime dateTime;
}
