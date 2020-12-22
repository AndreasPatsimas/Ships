package com.papei.pms.ships.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PositionDto {

    private String id;
    private Integer sourcemmsi;
    private String shipName;
    private String shipFlag;
    private Integer navigationalStatus;
    private Integer rateOfTurn;
    private Integer speedOverGround;
    private Integer courseOverGround;
    private Integer trueHeading;
    private Double lon;
    private Double lat;
    private LocalDateTime dateTime;
    private Integer imoNumber;
    private String callSign;
    private Integer shipType;
    private LocationDto location;
}
