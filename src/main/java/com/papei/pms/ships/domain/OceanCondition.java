package com.papei.pms.ships.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "oceancond")
public class OceanCondition {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private Double lon;
    private Double lat;
    private Double dpt;
    private Double wlv;
    private Double hs;
    private Double lm;
    private Double dir;
    private Long ts;
}
