package com.papei.pms.ships.controllers;

import com.papei.pms.ships.dto.AnfrDto;
import com.papei.pms.ships.services.AnfrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/anfrs")
@RestController
@Slf4j
public class AnfrController {

    @Autowired
    private AnfrService anfrService;

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    AnfrDto findById(@PathVariable("id") String id) {

        log.info("Fetch anfr by id {}", id);

        return anfrService.fetchById(id);
    }
}
