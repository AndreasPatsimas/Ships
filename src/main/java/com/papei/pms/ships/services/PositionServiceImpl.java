package com.papei.pms.ships.services;


import com.papei.pms.ships.domain.Position;
import com.papei.pms.ships.dto.CoordinateDto;
import com.papei.pms.ships.dto.PositionDto;
import com.papei.pms.ships.dto.PositionInsideBoxDto;
import com.papei.pms.ships.enums.Flag;
import com.papei.pms.ships.repositories.PositionRepository;
import com.papei.pms.ships.utils.MathCalculations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.*;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class PositionServiceImpl implements PositionService {

    private final Sort SORT_BY = Sort.by(Sort.Direction.ASC, "t");

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

        List<Position> positionList = positionRepository.findPositionByMmsi(sourcemmsi, SORT_BY);

        List<PositionDto> positions = convert(positionList);

        log.info("Fetch all positions by sourcemmsi {} process end", sourcemmsi);

        return positions;
    }

    @Override
    public List<List<CoordinateDto>> fetchDistanceJoin(Integer sourcemmsiOne,
                                                       Integer sourcemmsiTwo,
                                                       Double value,
                                                       LocalDateTime dateTimeFrom,
                                                       LocalDateTime dateTimeTo) {

        log.info("Distance join for mmsi_one: {} and mmsi_two: {} process begins", sourcemmsiOne, sourcemmsiTwo);

        List<List<CoordinateDto>> distanceJoin = new ArrayList<>();

        List<Position> positionsOne = positionRepository.findPositionByMmsiAndT(sourcemmsiOne,
                Date.from(dateTimeFrom.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000,
                Date.from(dateTimeTo.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000);

        List<Position> positionsTwo = positionRepository.findPositionByMmsiAndT(sourcemmsiTwo,
                Date.from(dateTimeFrom.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000,
                Date.from(dateTimeTo.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000);

        positionsOne.forEach(positionOne -> {

            List<Position> posTwo = positionsTwo.stream()
                    .filter(pos -> pos.getT().equals(positionOne.getT()))
                    .collect(Collectors.toList());

            if (!posTwo.isEmpty()){

                Position positionTwo = posTwo.get(0);

                if (MathCalculations.distance(positionOne.getLocation().getCoordinates().getLon(), positionOne.getLocation().getCoordinates().getLat(),
                        positionTwo.getLocation().getCoordinates().getLon(), positionTwo.getLocation().getCoordinates().getLat()) <= value)
                    distanceJoin.add(Stream
                            .of(CoordinateDto.builder().lon(positionOne.getLocation().getCoordinates().getLon())
                                            .lat(positionOne.getLocation().getCoordinates().getLat()).build(),
                                    CoordinateDto.builder().lon(positionTwo.getLocation().getCoordinates().getLon())
                                            .lat(positionTwo.getLocation().getCoordinates().getLat()).build())
                            .collect(Collectors.toList()));
            }
        });

        log.info("Distance join for mmsi_one: {} and mmsi_two: {} process end", sourcemmsiOne, sourcemmsiTwo);

        return distanceJoin;
    }

    @Override
    public List<List<CoordinateDto>> fetchDistanceJoin(Integer sourcemmsiOne, Integer sourcemmsiTwo, Double value) {

        log.info("Distance join for mmsi_one: {} and mmsi_two: {} process begins", sourcemmsiOne, sourcemmsiTwo);

        List<List<CoordinateDto>> distanceJoin = new ArrayList<>();

        List<Position> positionsOne = positionRepository.findPositionByMmsi(sourcemmsiOne);

        List<Position> positionsTwo = positionRepository.findPositionByMmsi(sourcemmsiTwo);

        positionsOne.forEach(positionOne -> positionsTwo.forEach(positionTwo -> {

            if (Point2D.distance(positionOne.getLocation().getCoordinates().getLon(), positionOne.getLocation().getCoordinates().getLat(),
                    positionTwo.getLocation().getCoordinates().getLon(), positionTwo.getLocation().getCoordinates().getLat()) <= value)
                distanceJoin.add(Stream
                        .of(CoordinateDto.builder().lon(positionOne.getLocation().getCoordinates().getLon())
                                        .lat(positionOne.getLocation().getCoordinates().getLat()).build(),
                                CoordinateDto.builder().lon(positionTwo.getLocation().getCoordinates().getLon())
                                        .lat(positionTwo.getLocation().getCoordinates().getLat()).build())
                        .collect(Collectors.toList()));
        }));

        log.info("Distance join for mmsi_one: {} and mmsi_two: {} process end", sourcemmsiOne, sourcemmsiTwo);

        return distanceJoin;
    }

    @Override
    public List<PositionDto> fetchByShipFlag(Flag shipFlag) {

        log.info("Fetch all positions by flag {} process begins", shipFlag.code());

        List<Position> positionList = positionRepository.findPositionByShipFlag(shipFlag.code(), SORT_BY);

        List<PositionDto> positions = convert(positionList);

        log.info("Fetch all positions by flag {} process end", shipFlag.code());

        return positions;
    }

    @Override
    public List<PositionDto> fetchPositionsNearGivenPoint(Double longitude,
                                                          Double latitude,
                                                          Integer maxDistance,
                                                          Integer minDistance,
                                                          LocalDateTime dateTimeFrom,
                                                          LocalDateTime dateTimeTo) {

        log.info("Fetch all positions near to our point[{}, {}] process begins", longitude, latitude);

        List<Position> positionList = positionRepository
                .findPositionsNearGivenPoint(longitude, latitude, maxDistance, minDistance,
                        Date.from(dateTimeFrom.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000,
                        Date.from(dateTimeTo.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000,
                        SORT_BY);

        List<PositionDto> positions = convert(positionList);

        log.info("Fetch all positions near to our point[{}, {}] process end", longitude, latitude);

        return positions;
    }

    @Override
    public List<PositionDto> fetchPositionsNearGivenPoint(Double longitude, Double latitude, Integer maxDistance, Integer minDistance) {
        log.info("Fetch all positions near to our point[{}, {}] process begins", longitude, latitude);

        List<Position> positionList = positionRepository
                .findPositionsNearGivenPoint(longitude, latitude, maxDistance, minDistance, SORT_BY);

        List<PositionDto> positions = convert(positionList);

        log.info("Fetch all positions near to our point[{}, {}] process end", longitude, latitude);

        return positions;
    }

    @Override
    public Page<PositionDto> knn(Double longitude,
                                 Double latitude,
                                 LocalDateTime dateTimeFrom,
                                 LocalDateTime dateTimeTo,
                                 Pageable pageable) {

        log.info("K-nn near to our point[{}, {}] process begins", longitude, latitude);

        Page<Position> positions = positionRepository.knn(longitude, latitude,
                Date.from(dateTimeFrom.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000,
                Date.from(dateTimeTo.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000,
                pageable);

        return getPositionDtosPage(pageable, positions);
    }

    @Override
    public Page<PositionDto> knn(Double longitude, Double latitude, Pageable pageable) {
        log.info("K-nn near to our point[{}, {}] process begins", longitude, latitude);

        Page<Position> positions = positionRepository.knn(longitude, latitude, pageable);

        return getPositionDtosPage(pageable, positions);
    }

    @Override
    public List<PositionDto> fetchPositionsWithinCertainRadius(Double longitude,
                                                               Double latitude,
                                                               Double radius,
                                                               LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {

        log.info("Fetch all positions around center[{}, {}] with radius: {} process begins", longitude, latitude, radius);

        Point center = new Point(longitude, latitude);
        Distance radiusCircle = new Distance(radius, Metrics.KILOMETERS);
        Circle circle = new Circle(center, radiusCircle);

        List<Position> positionList = positionRepository.findByLocationWithinAndTBetween(circle,
                Date.from(dateTimeFrom.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000,
                Date.from(dateTimeTo.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000);

        List<PositionDto> positions = convert(positionList);

        log.info("Fetch all positions around center[{}, {}] with radius: {} process end", longitude, latitude, radius);

        return positions;
    }

    @Override
    public List<PositionDto> fetchPositionsWithinCertainRadius(Double longitude, Double latitude, Double radius) {
        log.info("Fetch all positions around center[{}, {}] with radius: {} process begins", longitude, latitude, radius);

        Point center = new Point(longitude, latitude);
        Distance radiusCircle = new Distance(radius, Metrics.KILOMETERS);
        Circle circle = new Circle(center, radiusCircle);

        List<Position> positionList = positionRepository.findByLocationWithin(circle);

        List<PositionDto> positions = convert(positionList);

        log.info("Fetch all positions around center[{}, {}] with radius: {} process end", longitude, latitude, radius);

        return positions;
    }

    @Override
    public List<PositionDto> fetchPositionsInsideBox(PositionInsideBoxDto positionInsideBoxDto) {

        log.info("Fetch all positions inside box process begins");

        List<Position> positionList;

        if (!ObjectUtils.isEmpty(positionInsideBoxDto.getDateTimeFrom()) && !ObjectUtils.isEmpty(positionInsideBoxDto.getDateTimeTo()))
            positionList = positionRepository.findPositionsInsideBox(positionInsideBoxDto.getCoordinates1(),
                    positionInsideBoxDto.getCoordinates2(), positionInsideBoxDto.getCoordinates3(),
                    positionInsideBoxDto.getCoordinates4(), positionInsideBoxDto.getCoordinates1(),
                    Date.from(positionInsideBoxDto.getDateTimeFrom().atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000,
                    Date.from(positionInsideBoxDto.getDateTimeTo().atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000);
        else
            positionList = positionRepository.findPositionsInsideBox(positionInsideBoxDto.getCoordinates1(),
                    positionInsideBoxDto.getCoordinates2(), positionInsideBoxDto.getCoordinates3(),
                    positionInsideBoxDto.getCoordinates4(), positionInsideBoxDto.getCoordinates1());

        List<PositionDto> positions = convert(positionList);

        log.info("Fetch all positions inside box process end");

        return positions;
    }

    private Page<PositionDto> getPositionDtosPage(Pageable pageable, Page<Position> positions){

        Page<PositionDto> positionDtosPage;

        Long total;
        if (ObjectUtils.isEmpty(positions)) {
            total = 0L;
            positionDtosPage = buildPagedResults(Collections.EMPTY_LIST, total, pageable);

        } else {

            List<PositionDto> positionDtos = new ArrayList<>(positions.getContent().size());
            positions.forEach(position -> positionDtos.add(conversionService.convert(position, PositionDto.class)));
            total = positions.getTotalElements();
            positionDtosPage = buildPagedResults(positionDtos, total, pageable);
        }

        log.info("Find all positions end. Total positions: {}, Page size {}, Page {} of {} pages.",
                total, pageable.getPageSize(), pageable.getPageNumber(), positions.getTotalPages());

        return positionDtosPage;
    }

    private <T> Page<T> buildPagedResults(List<T> list, Long countResult, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        return new PageImpl<>
                (list, PageRequest.of(pageable.getPageNumber(), pageSize), countResult);
    }

    private List<PositionDto> convert(List<Position> positionList){

        return positionList.stream()
                .map(position -> conversionService.convert(position, PositionDto.class))
                .collect(Collectors.toList());
    }
}
