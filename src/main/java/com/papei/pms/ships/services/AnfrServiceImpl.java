package com.papei.pms.ships.services;

import com.papei.pms.ships.domain.Anfr;
import com.papei.pms.ships.dto.AnfrDto;
import com.papei.pms.ships.repositories.AnfrRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AnfrServiceImpl implements AnfrService {

    private AnfrRepository anfrRepository;

    private ConversionService conversionService;

    @Autowired
    public AnfrServiceImpl(AnfrRepository anfrRepository, ConversionService conversionService) {
        this.anfrRepository = anfrRepository;
        this.conversionService = conversionService;
    }

    @Override
    public AnfrDto fetchById(String id) {

        log.info("Fetch anfr by id {} process begins", id);

        Optional<Anfr> optionalAnfr = anfrRepository.findById(id);

        AnfrDto anfrDto = optionalAnfr
                .map(anfr -> conversionService.convert(anfr, AnfrDto.class))
                .orElse(null);

        log.info("Fetch anfr by id {} process end", id);

        return anfrDto;
    }
}
