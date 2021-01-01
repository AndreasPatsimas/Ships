package com.papei.pms.ships.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PositionInsideBoxDto {

    List<Double> coordinates1;
    List<Double> coordinates2;
    List<Double> coordinates3;
    List<Double> coordinates4;
    LocalDateTime dateTimeFrom;
    LocalDateTime dateTimeTo;
}
