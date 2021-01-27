package com.papei.pms.ships.services;

import com.papei.pms.ships.domain.PositionAggregation;
import com.papei.pms.ships.dto.CoordinateDto;
import com.papei.pms.ships.dto.PositionDto;
import com.papei.pms.ships.dto.PositionInsideBoxDto;
import com.papei.pms.ships.enums.Flag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public interface PositionService {

    List<PositionDto> fetchBySourcemmsi(Integer sourcemmsi);

    List<List<CoordinateDto>> fetchDistanceJoin(Integer sourcemmsiOne,
                                                Integer sourcemmsiTwo,
                                                Double value,
                                                LocalDateTime dateTimeFrom,
                                                LocalDateTime dateTimeTo);

    List<List<CoordinateDto>> fetchDistanceJoin(Integer sourcemmsiOne,
                                                Integer sourcemmsiTwo,
                                                Double value);

    List<PositionDto> fetchByShipFlag(Flag shipFlag);

    List<PositionAggregation> fetchByShipFlagGroupBySourceMmsi(Flag shipFlag);

    List<PositionDto> fetchPositionsNearGivenPoint(Double longitude,
                                                   Double latitude,
                                                   Integer maxDistance,
                                                   Integer minDistance,
                                                   LocalDateTime dateTimeFrom,
                                                   LocalDateTime dateTimeTo);

    List<PositionDto> fetchPositionsNearGivenPoint(Double longitude,
                                                   Double latitude,
                                                   Integer maxDistance,
                                                   Integer minDistance);

    Page<PositionDto> knn(Double longitude,
                          Double latitude,
                          LocalDateTime dateTimeFrom,
                          LocalDateTime dateTimeTo,
                          Pageable pageable);

    Page<PositionDto> knn(Double longitude, Double latitude, Pageable pageable);

    List<PositionDto> fetchPositionsWithinCertainRadius(Double longitude,
                                                        Double latitude,
                                                        Double radius,
                                                        LocalDateTime dateTimeFrom,
                                                        LocalDateTime dateTimeTo);

    List<PositionDto> fetchPositionsWithinCertainRadius(Double longitude, Double latitude, Double radius);

    List<PositionDto> fetchPositionsInsideBox(PositionInsideBoxDto positionInsideBoxDto);

    Set<Integer> complexQuery(CoordinateDto coordinatesA,
                              CoordinateDto coordinatesB,
                              CoordinateDto coordinatesC,
                              Long t);
}