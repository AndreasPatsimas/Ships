package com.papei.pms.ships.domain;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Area {

    private String type;
    private List<List<List<Double>>> coordinates;
}
