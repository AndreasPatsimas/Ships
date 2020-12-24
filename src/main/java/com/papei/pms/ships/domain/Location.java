package com.papei.pms.ships.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Location {

    private String type;
    private Coordinate coordinates;
}
