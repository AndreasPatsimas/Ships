package com.papei.pms.ships.convert;

import com.papei.pms.ships.domain.Coordinate;
import com.papei.pms.ships.domain.Location;
import com.papei.pms.ships.domain.Position;
import com.papei.pms.ships.dto.CoordinateDto;
import com.papei.pms.ships.dto.LocationDto;
import com.papei.pms.ships.dto.PositionDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Component
public class PositionToPositionDtoConverter implements Converter<Position, PositionDto> {

    @Override
    public PositionDto convert(Position position) {
        return PositionDto.builder()
                .id(position.getId())
                .sourcemmsi(position.getSourcemmsi())
                .shipName(position.getShipName())
                .shipFlag(position.getShipFlag())
                .navigationalStatus(position.getNavigationalstatus())
                .rateOfTurn(position.getRateofturn())
                .speedOverGround(position.getSpeedoverground())
                .courseOverGround(position.getCourseoverground())
                .trueHeading(position.getTrueheading())
                .lon(position.getLon())
                .lat(position.getLat())
                .dateTime(LocalDateTime
                        .ofInstant(Instant.ofEpochSecond(position.getT()),
                                TimeZone.getDefault().toZoneId()))
                .imoNumber(position.getImo_number())
                .callSign(position.getCallSign())
                .shipType(position.getShipType())
                .location(buildLocation(position.getLocation()))
                .build();
    }

    private LocationDto buildLocation(Location location){

        return LocationDto.builder()
                .type(location.getType())
                .coordinates(buildCoordinates(location.getCoordinates()))
                .build();
    }

    private CoordinateDto buildCoordinates(Coordinate coordinates){

        return CoordinateDto.builder()
                .lon(coordinates.getLon())
                .lat(coordinates.getLat())
                .build();
    }
}
