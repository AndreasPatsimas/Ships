package com.papei.pms.ships.convert;

import com.papei.pms.ships.domain.Location;
import com.papei.pms.ships.domain.Position;
import com.papei.pms.ships.dto.LocationDto;
import com.papei.pms.ships.dto.PositionDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;

@Component
public class PositionDtoToPositionConverter implements Converter<PositionDto, Position> {

    @Override
    public Position convert(PositionDto positionDto) {
        return Position.builder()
                .id(positionDto.getId())
                .sourcemmsi(positionDto.getSourcemmsi())
                .shipName(positionDto.getShipName())
                .shipFlag(positionDto.getShipFlag())
                .navigationalstatus(positionDto.getNavigationalStatus())
                .rateofturn(positionDto.getRateOfTurn())
                .speedoverground(positionDto.getSpeedOverGround())
                .courseoverground(positionDto.getCourseOverGround())
                .trueheading(positionDto.getTrueHeading())
                .lon(positionDto.getLon())
                .lat(positionDto.getLat())
                .t(Date.from(positionDto.getDateTime().atZone(ZoneId.systemDefault()).toInstant()).getTime())
                .imo_number(positionDto.getImoNumber())
                .callSign(positionDto.getCallSign())
                .ShipType(positionDto.getShipType())
                .location(buildLocation(positionDto.getLocation()))
                .build();
    }

    private Location buildLocation(LocationDto locationDto){

        return Location.builder()
                .type(locationDto.getType())
                .coordinates(locationDto.getCoordinates())
                .build();
    }
}
