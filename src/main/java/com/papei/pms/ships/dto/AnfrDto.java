package com.papei.pms.ships.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnfrDto {

    private String id;
    private String maritimeArea;
    private String registrationNumber;
    private String imoNumber;
    private String shipName;
    private String callSign;
    private String mmsi;
    private String shipType;
    private Integer length;
    private Integer tonnage;
    private String tonnageUnit;
    private String materielOnBoard;
    private String atisCode;
    private String radioLicenseStatus;
    private String dateFirstLicense;
    private String dateInactivityLicense;
}
