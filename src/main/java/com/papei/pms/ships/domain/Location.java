package com.papei.pms.ships.domain;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Location {

    private String type;
    private String coordinates;
}
