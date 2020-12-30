package com.papei.pms.ships.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PolygonRequestDto {

    private String name;
    private List<Double> p1;
    private List<Double> p2;
    private List<Double> p3;
    private List<Double> p4;
    private LocalDateTime dateTime;
}
