package com.papei.pms.ships.controllers;

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
    List<PositionDto> findByShipFlag(@PathVariable("shipFlag") Flag shipFlag) {

        log.info("Fetch all positions by flag {}", shipFlag.code());

        return positionService.fetchByShipFlag(shipFlag);
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

    @GetMapping(value = "/spatio-temporal/k-nn/{longitude}/{latitude}/{dateTimeFrom}/{dateTimeTo}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    Page<PositionDto> knn(@RequestParam Integer page,
                         @RequestParam Integer pageSize,
                         @RequestParam(required = false) String sortBy,
                         @RequestParam(required = false) String sortDirection,
                         @PathVariable("longitude") Double longitude,
                         @PathVariable("latitude") Double latitude,
                         @PathVariable("dateTimeFrom") String dateTimeFrom,
                         @PathVariable("dateTimeTo") String dateTimeTo) {

        log.info("K-nn near to our point[{}, {}]", longitude, latitude);

        Pageable pageable;

        if (ObjectUtils.isEmpty(sortBy) || ObjectUtils.isEmpty(sortDirection))
            pageable = PageRequest.of(page, pageSize);
        else {
            Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
            pageable = PageRequest.of(page, pageSize, sort);
        }

        return positionService.knn(longitude, latitude,
                LocalDateTime.parse(dateTimeFrom), LocalDateTime.parse(dateTimeTo), pageable);
    }

    @GetMapping(value = "/spatial/k-nn/{longitude}/{latitude}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    Page<PositionDto> knn(@RequestParam Integer page,
                         @RequestParam Integer pageSize,
                         @RequestParam(required = false) String sortBy,
                         @RequestParam(required = false) String sortDirection,
                         @PathVariable("longitude") Double longitude,
                         @PathVariable("latitude") Double latitude) {

        log.info("K-nn near to our point[{}, {}]", longitude, latitude);

        Pageable pageable;

        if (ObjectUtils.isEmpty(sortBy) || ObjectUtils.isEmpty(sortDirection))
            pageable = PageRequest.of(page, pageSize);
        else {
            Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
            pageable = PageRequest.of(page, pageSize, sort);
        }

        return positionService.knn(longitude, latitude, pageable);
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
}
