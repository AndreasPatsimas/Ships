package com.papei.pms.ships.controllers;

import com.papei.pms.ships.dto.EuropeanVesselRegisterDto;
import com.papei.pms.ships.services.EuropeanVesselRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/european-vessels")
@RestController
@Slf4j
public class EuropeanVesselRegisterController {

    @Autowired
    private EuropeanVesselRegisterService europeanVesselRegisterService;

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    EuropeanVesselRegisterDto findEuropeanVessel(@PathVariable("id") String id) {

        log.info("Fetch European Vessel [id:{}]", id);

        return europeanVesselRegisterService.fetchById(id);
    }
}
