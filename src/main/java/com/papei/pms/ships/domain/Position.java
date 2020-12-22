package com.papei.pms.ships.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "positions")
public class Position {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private Integer sourcemmsi;
    private String shipName;
    private String shipFlag;
    private Integer navigationalstatus;
    private Integer rateofturn;
    private Integer speedoverground;
    private Integer courseoverground;
    private Integer trueheading;
    private Double lon;
    private Double lat;
    private Long t;
    private Integer imo_number;
    private String callSign;
    private Integer ShipType;
    private Location location;
}
