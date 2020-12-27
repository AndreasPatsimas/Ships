package com.papei.pms.ships.domain;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "areas")
public class Polygon {

    @Transient
    public static final String SEQUENCE_NAME = "areas_sequence";

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private String name;
    private Area area;
}
