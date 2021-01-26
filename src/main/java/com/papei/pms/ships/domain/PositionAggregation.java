package com.papei.pms.ships.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document
public class PositionAggregation {

    @JsonProperty("sourcemmsi")
    @MongoId(FieldType.OBJECT_ID)
    private String id;


    @JsonIgnore
    private Integer sourcemmsi;

    @JsonProperty("locations")
    private List<Location> locations;
}
