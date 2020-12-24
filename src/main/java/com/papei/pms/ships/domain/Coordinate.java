package com.papei.pms.ships.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Coordinate {

    private Double lon;
    private Double lat;
}
