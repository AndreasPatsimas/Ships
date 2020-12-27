package com.papei.pms.ships.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "anfr")
public class Anfr {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private String maritime_area;
    private String registration_number;
    private String imo_number;
    private String ship_name;
    private String callsign;
    private String mmsi;
    private String shiptype;
    private Integer length;
    private Integer tonnage;
    private String tonnage_unit;
    private String materiel_onboard;
    private String atis_code;
    private String radio_license_status;
    private String date_first_license;
    private String date_inactivity_license;
}
