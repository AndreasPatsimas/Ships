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

        log.info("Fetch all ships by flag {}", shipFlag.code());

        return positionService.fetchByShipFlag(shipFlag);
    }
}
