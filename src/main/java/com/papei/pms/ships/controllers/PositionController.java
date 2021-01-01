package com.papei.pms.ships.controllers;

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

    @GetMapping(value = "/point/{longitude}/{latitude}/{maxDistance}/{minDistance}/{dateTimeFrom}/{dateTimeTo}",
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

    @GetMapping(value = "/k-nn/{longitude}/{latitude}/{dateTimeFrom}/{dateTimeTo}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    Page<PositionDto> knn(@RequestParam Integer page,
                         @RequestParam Integer pageSize,
                         @RequestParam String sortBy,
                         @RequestParam String sortDirection,
                         @PathVariable("longitude") Double longitude,
                         @PathVariable("latitude") Double latitude,
                         @PathVariable("dateTimeFrom") String dateTimeFrom,
                         @PathVariable("dateTimeTo") String dateTimeTo) {

        log.info("K-nn near to our point[{}, {}]", longitude, latitude);

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return positionService.knn(longitude, latitude,
                LocalDateTime.parse(dateTimeFrom), LocalDateTime.parse(dateTimeTo), pageable);
    }

    @GetMapping(value = "/circle/{longitude}/{latitude}/{radius}/{dateTimeFrom}/{dateTimeTo}",
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

    @PostMapping(value = "/box", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<PositionDto> findPositionsInsideBox(@RequestBody PositionInsideBoxDto positionInsideBoxDto) {

        log.info("Fetch all positions inside box");

        return positionService.fetchPositionsInsideBox(positionInsideBoxDto);
    }
}
