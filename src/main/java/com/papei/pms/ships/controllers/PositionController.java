package com.papei.pms.ships.controllers;

import com.papei.pms.ships.dto.PositionDto;
import com.papei.pms.ships.enums.Flag;
import com.papei.pms.ships.services.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/point/{longitude}/{latitude}/{maxDistance}/{minDistance}/{t}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<PositionDto> findPositionsNearGivenPoint(@PathVariable("longitude") Double longitude,
                                                  @PathVariable("latitude") Double latitude,
                                                  @PathVariable("maxDistance") Integer maxDistance,
                                                  @PathVariable("minDistance") Integer minDistance,
                                                  @PathVariable("t") Long t) {

        log.info("Fetch all positions near to our point[{}, {}]", longitude, latitude);

        return positionService.fetchPositionsNearGivenPoint(longitude, latitude, maxDistance, minDistance, t);
    }

    @GetMapping(value = "/circle/{longitude}/{latitude}/{radius}/{t}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<PositionDto> findPositionsWithinCertainRadius(@PathVariable("longitude") Double longitude,
                                                       @PathVariable("latitude") Double latitude,
                                                       @PathVariable("radius") Double radius,
                                                       @PathVariable("t") Long t) {

        log.info("Fetch all positions around center[{}, {}] with radius: {}", longitude, latitude, radius);

        return positionService.fetchPositionsWithinCertainRadius(longitude, latitude, radius,t);
    }
}
