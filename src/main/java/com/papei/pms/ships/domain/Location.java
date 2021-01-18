package com.papei.pms.ships.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Location {

    private Coordinate coordinates;
    private String type;
}
