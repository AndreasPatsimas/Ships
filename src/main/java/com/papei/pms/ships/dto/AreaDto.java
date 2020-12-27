package com.papei.pms.ships.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AreaDto {

    private String type;
    private List<List<List<Double>>> coordinates;
}
