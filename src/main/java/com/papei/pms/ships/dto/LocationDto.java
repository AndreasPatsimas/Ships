package com.papei.pms.ships.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LocationDto {

    private String type;
    private String coordinates;
}
