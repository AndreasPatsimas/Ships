package com.papei.pms.ships.controllers;

import com.papei.pms.ships.domain.PositionAggregation;
import com.papei.pms.ships.dto.ComplexRequestDto;
import com.papei.pms.ships.dto.CoordinateDto;
import com.papei.pms.ships.dto.PositionDto;
import com.papei.pms.ships.dto.PositionInsideBoxDto;
import com.papei.pms.ships.enums.Flag;
import com.papei.pms.ships.services.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/positions")
@RestController
@Slf4j
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping(value = "/{sourcemmsi}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<PositionDto> findBySourcemmsi(@PathVariable("sourcemmsi") Integer sourcemmsi) {

        log.info("Fetch all positions by sourcemmsi {}", sourcemmsi);

        return positionService.fetchBySourcemmsi(sourcemmsi);
    }

    @GetMapping(value = "/flag/{shipFlag}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<PositionAggregation> findByShipFlag(@PathVariable("shipFlag") Flag shipFlag) {

        log.info("Fetch all positions by flag {}", shipFlag.code());

        return positionService.fetchByShipFlagGroupBySourceMmsi(shipFlag);//.fetchByShipFlag(shipFlag);
    }

    @GetMapping(value = "/spatio-temporal/point/{longitude}/{latitude}/{maxDistance}/{minDistance}/{dateTimeFrom}/{dateTimeTo}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<PositionDto> findPositionsNearGivenPoint(@PathVariable("longitude") Double longitude,
                                                  @PathVariable("latitude") Double latitude,
                                                  @PathVariable("maxDistance") Integer maxDistance,
                                                  @PathVariable("minDistance") Integer minDistance,
                                                  @PathVariable("dateTimeFrom") String dateTimeFrom,
                                                  @PathVariable("dateTimeTo") String dateTimeTo) {

        log.info("Fetch all positions near to our point[{}, {}]", longitude, latitude);

        return positionService.fetchPositionsNearGivenPoint(longitude, latitude, maxDistance, minDistance,
                LocalDateTime.parse(dateTimeFrom), LocalDateTime.parse(dateTimeTo));
    }

    @GetMapping(value = "/spatial/point/{longitude}/{latitude}/{maxDistance}/{minDistance}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<PositionDto> findPositionsNearGivenPoint(@PathVariable("longitude") Double longitude,
                                                  @PathVariable("latitude") Double latitude,
                                                  @PathVariable("maxDistance") Integer maxDistance,
                                                  @PathVariable("minDistance") Integer minDistance) {

        log.info("Fetch all positions near to our point[{}, {}]", longitude, latitude);

        return positionService.fetchPositionsNearGivenPoint(longitude, latitude, maxDistance, minDistance);
    }

    @GetMapping(value = "/spatio-temporal/k-nn/{longitude}/{latitude}/{dateTimeFrom}/{dateTimeTo}/{limit}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<PositionDto> knn(@PathVariable("longitude") Double longitude,
                         @PathVariable("latitude") Double latitude,
                         @PathVariable("dateTimeFrom") String dateTimeFrom,
                         @PathVariable("dateTimeTo") String dateTimeTo,
                         @PathVariable("limit") Integer limit) {

        log.info("K-nn near to our point[{}, {}]", longitude, latitude);

        return positionService.knn(longitude, latitude,
                LocalDateTime.parse(dateTimeFrom), LocalDateTime.parse(dateTimeTo), limit);
    }

    @GetMapping(value = "/spatial/k-nn/{longitude}/{latitude}/{limit}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<PositionDto> knn(@PathVariable("longitude") Double longitude,
                          @PathVariable("latitude") Double latitude,
                          @PathVariable("limit") Integer limit) {

        log.info("K-nn near to our point[{}, {}]", longitude, latitude);

        return positionService.knn(longitude, latitude, limit);
    }

    @GetMapping(value = "/spatio-temporal/circle/{longitude}/{latitude}/{radius}/{dateTimeFrom}/{dateTimeTo}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<PositionDto> findPositionsWithinCertainRadius(@PathVariable("longitude") Double longitude,
                                                       @PathVariable("latitude") Double latitude,
                                                       @PathVariable("radius") Double radius,
                                                       @PathVariable("dateTimeFrom") String dateTimeFrom,
                                                       @PathVariable("dateTimeTo") String dateTimeTo) {

        log.info("Fetch all positions around center[{}, {}] with radius: {}", longitude, latitude, radius);

        return positionService.fetchPositionsWithinCertainRadius(longitude, latitude, radius, LocalDateTime.parse(dateTimeFrom),
                LocalDateTime.parse(dateTimeTo));
    }

    @GetMapping(value = "/spatial/circle/{longitude}/{latitude}/{radius}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<PositionDto> findPositionsWithinCertainRadius(@PathVariable("longitude") Double longitude,
                                                       @PathVariable("latitude") Double latitude,
                                                       @PathVariable("radius") Double radius) {

        log.info("Fetch all positions around center[{}, {}] with radius: {}", longitude, latitude, radius);

        return positionService.fetchPositionsWithinCertainRadius(longitude, latitude, radius);
    }

    @PostMapping(value = "/box", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<PositionDto> findPositionsInsideBox(@RequestBody PositionInsideBoxDto positionInsideBoxDto) {

        log.info("Fetch all positions inside box");

        return positionService.fetchPositionsInsideBox(positionInsideBoxDto);
    }

    @GetMapping(value = "/spatio-temporal/distance-join/{sourcemmsiOne}/{sourcemmsiTwo}/{value}/{dateTimeFrom}/{dateTimeTo}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<List<CoordinateDto>> findDistanceJoin(@PathVariable("sourcemmsiOne") Integer sourcemmsiOne,
                                               @PathVariable("sourcemmsiTwo") Integer sourcemmsiTwo,
                                               @PathVariable("value") Double value,
                                               @PathVariable("dateTimeFrom") String dateTimeFrom,
                                               @PathVariable("dateTimeTo") String dateTimeTo) {

        log.info("Distance join for mmsi_one: {} and mmsi_two: {}", sourcemmsiOne, sourcemmsiTwo);

        return positionService.fetchDistanceJoin(sourcemmsiOne, sourcemmsiTwo, value, LocalDateTime.parse(dateTimeFrom),
                LocalDateTime.parse(dateTimeTo));
    }

    @GetMapping(value = "/spatio-temporal/similar-trajectories/{sourcemmsiOne}/{sourcemmsiTwo}/{value}/{dateTimeFrom}/{dateTimeTo}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<List<CoordinateDto>> findSimilarTrajectories(@PathVariable("sourcemmsiOne") Integer sourcemmsiOne,
                                                      @PathVariable("sourcemmsiTwo") Integer sourcemmsiTwo,
                                                      @PathVariable("value") Double value,
                                                      @PathVariable("dateTimeFrom") String dateTimeFrom,
                                                      @PathVariable("dateTimeTo") String dateTimeTo) {

        log.info("Similar Trajectories for mmsi_one: {} and mmsi_two: {}", sourcemmsiOne, sourcemmsiTwo);


        return positionService.fetchSimilarTrajectories(sourcemmsiOne, sourcemmsiTwo, value, LocalDateTime.parse(dateTimeFrom),
                LocalDateTime.parse(dateTimeTo));
    }

    @PostMapping(value = "/complex",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Set<Integer> complex(@RequestBody ComplexRequestDto complexRequestDto) {

        log.info("Complex query");

        return positionService.complexQuery(complexRequestDto.getCoordinatesA(), complexRequestDto.getCoordinatesB(),
                complexRequestDto.getCoordinatesC(), complexRequestDto.getT());
    }
}
