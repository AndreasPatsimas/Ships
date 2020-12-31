package com.papei.pms.ships.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "europeanvesselregister")
public class EuropeanVesselRegister {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private String countrycode;
    private String cfr;
    private String eventcode;
    private Long eventstartdate;
    private Long eventenddate;
    private String licenseind;
    private String registrationnbr;
    private String extmarking;
    private String vesselname;
    private String portcode;
    private String portname;
    private String ircscode;
    private String ircs;
    private String vmscode;
    private String gearmaincode;
    private String gearseccode;
    private Double loa;
    private Double lbp;
    private Double tonref;
    private Double tongt;
    private Double tonoth;
    private Double tongts;
    private Integer powermain;
    private Integer poweraux;
    private Integer hullmaterial;
    private Integer comyear;
    private Integer commonth;
    private Integer comday;
    private String segment;
    private String expcountry;
    private String exptype;
    private String publicaidcode;
    private String decisiondate;
    private String decisionsegcode;
    private Integer constructionyear;
    private String constructionplace;
}
