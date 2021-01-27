package com.papei.pms.ships.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ComplexRequestDto {

    private CoordinateDto coordinatesA;
    private CoordinateDto coordinatesB;
    private CoordinateDto coordinatesC;
    private Long t;
}
