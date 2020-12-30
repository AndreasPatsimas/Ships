package com.papei.pms.ships.convert;

import com.papei.pms.ships.domain.Area;
import com.papei.pms.ships.domain.Polygon;
import com.papei.pms.ships.dto.AreaDto;
import com.papei.pms.ships.dto.PolygonResponseDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Component
public class PolygonToPolygonResponseDtoConverter implements Converter<Polygon, PolygonResponseDto> {

    @Override
    public PolygonResponseDto convert(Polygon polygon) {
        return PolygonResponseDto.builder()
                .id(polygon.getId())
                .name(polygon.getName())
                .area(buildArea(polygon.getArea()))
                .dateTime(LocalDateTime
                        .ofInstant(Instant.ofEpochSecond(polygon.getT()),
                                TimeZone.getDefault().toZoneId()))
                .build();
    }

    private AreaDto buildArea(Area area){

        return AreaDto.builder()
                .type(area.getType())
                .coordinates(area.getCoordinates())
                .build();
    }
}
