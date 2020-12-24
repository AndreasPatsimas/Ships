package com.papei.pms.ships.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CoordinateDto {

    private Double lon;
    private Double lat;
}
