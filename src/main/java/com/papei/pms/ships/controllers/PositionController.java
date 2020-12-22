package com.papei.pms.ships.controllers;

import com.papei.pms.ships.dto.PositionDto;
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
}
