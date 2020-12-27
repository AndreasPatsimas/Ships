package com.papei.pms.ships.convert;

import com.papei.pms.ships.domain.Area;
import com.papei.pms.ships.domain.Polygon;
import com.papei.pms.ships.dto.PolygonRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PolygonRequestDtoToPolygonConverter implements Converter<PolygonRequestDto, Polygon> {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public Polygon convert(PolygonRequestDto polygonRequestDto) {

        return Polygon.builder()
                .name(polygonRequestDto.getName())
                .area(buildArea(polygonRequestDto))
                .build();
    }

    private Area buildArea(PolygonRequestDto polygonRequestDto){

        List<List<Double>> coords = Stream
                .of(polygonRequestDto.getP1(), polygonRequestDto.getP2(),
                    polygonRequestDto.getP3(), polygonRequestDto.getP4(), polygonRequestDto.getP1())
                .collect(Collectors.toList());

        List<List<List<Double>>> coordinates = Stream.of(coords).collect(Collectors.toList());

        return Area.builder()
                .type("Polygon")
                .coordinates(coordinates)
                .build();
    }
}
