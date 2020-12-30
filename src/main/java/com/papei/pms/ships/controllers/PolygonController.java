package com.papei.pms.ships.controllers;

import com.papei.pms.ships.dto.PolygonRequestDto;
import com.papei.pms.ships.services.PolygonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/areas")
@RestController
@Slf4j
public class PolygonController {

    @Autowired
    private PolygonService polygonService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity savePolygon(@RequestBody PolygonRequestDto polygonRequestDto) {
        log.info("Save Polygon[{}]", polygonRequestDto.toString());
        polygonService.savePolygon(polygonRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{longitude}/{latitude}/{dateTime}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    boolean checkIfShipExistsInsidePolygon(@PathVariable("longitude") Double longitude,
                                           @PathVariable("latitude") Double latitude,
                                           @PathVariable("dateTime") String dateTime) {

        log.info("Check if ship with coordinates: [{}, {}] exists inside polygon", longitude, latitude);

        return polygonService.checkIfShipExistsInsidePolygon(longitude, latitude, LocalDateTime.parse(dateTime));
    }
}
