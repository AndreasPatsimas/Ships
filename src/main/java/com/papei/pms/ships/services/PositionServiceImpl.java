package com.papei.pms.ships.services;


import com.papei.pms.ships.domain.Position;
import com.papei.pms.ships.dto.PositionDto;
import com.papei.pms.ships.enums.Flag;
import com.papei.pms.ships.repositories.PositionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PositionServiceImpl implements PositionService {

    private PositionRepository positionRepository;

    private ConversionService conversionService;

    @Autowired
    public PositionServiceImpl(PositionRepository positionRepository, ConversionService conversionService) {
        this.positionRepository = positionRepository;
        this.conversionService = conversionService;
    }

    @Override
    public List<PositionDto> fetchBySourcemmsi(Integer sourcemmsi) {

        log.info("Fetch all positions by sourcemmsi {} process begins", sourcemmsi);

        List<Position> positionList = positionRepository.findPositionByMmsi(sourcemmsi);

        List<PositionDto> positions = convert(positionList);

        log.info("Fetch all positions by sourcemmsi {} process end", sourcemmsi);

        return positions;
    }

    @Override
    public List<PositionDto> fetchByShipFlag(Flag shipFlag) {

        log.info("Fetch all positions by flag {} process begins", shipFlag.code());

        List<Position> positionList = positionRepository.findPositionByShipFlag(shipFlag.code());

        List<PositionDto> positions = convert(positionList);

        log.info("Fetch all positions by flag {} process end", shipFlag.code());

        return positions;
    }

    @Override
    public List<PositionDto> fetchPositionsNearGivenPoint(Double longitude,
                                                          Double latitude,
                                                          Integer maxDistance,
                                                          Integer minDistance,
                                                          LocalDateTime dateTime) {

        log.info("Fetch all positions near to our point[{}, {}] process begins", longitude, latitude);

        List<Position> positionList = positionRepository
                .findPositionsNearGivenPoint(longitude, latitude, maxDistance, minDistance,
                        Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000);

        List<PositionDto> positions = convert(positionList);

        log.info("Fetch all positions near to our point[{}, {}] process end", longitude, latitude);

        return positions;
    }

    @Override
    public List<PositionDto> fetchPositionsWithinCertainRadius(Double longitude,
                                                               Double latitude,
                                                               Double radius,
                                                               LocalDateTime dateTime) {

        log.info("Fetch all positions around center[{}, {}] with radius: {} process begins", longitude, latitude, radius);

        Point center = new Point(longitude, latitude);
        Distance radiusCircle = new Distance(radius, Metrics.KILOMETERS);
        Circle circle = new Circle(center, radiusCircle);

        List<Position> positionList = positionRepository.findByLocationWithinAndT(circle,
                Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000);

        List<PositionDto> positions = convert(positionList);

        log.info("Fetch all positions around center[{}, {}] with radius: {} process end", longitude, latitude, radius);

        return positions;
    }

    private List<PositionDto> convert(List<Position> positionList){

        return positionList.stream()
                .map(position -> conversionService.convert(position, PositionDto.class))
                .collect(Collectors.toList());
    }
}
