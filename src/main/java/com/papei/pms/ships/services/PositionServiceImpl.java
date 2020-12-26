package com.papei.pms.ships.services;


import com.papei.pms.ships.domain.Position;
import com.papei.pms.ships.dto.PositionDto;
import com.papei.pms.ships.enums.Flag;
import com.papei.pms.ships.repositories.PositionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PositionServiceImpl implements PositionService {

    private PositionRepository positionRepository;

    private ConversionService conversionService;

    @Autowired
    public PositionServiceImpl(PositionRepository positionRepository, ConversionService conversionService) {
        this.positionRepository = positionRepository;
        this.conversionService = conversionService;
    }

    @Override
    public List<PositionDto> fetchBySourcemmsi(Integer sourcemmsi) {

        log.info("Fetch all positions by sourcemmsi {} process begins", sourcemmsi);

        List<Position> positionList = positionRepository.findPositionByMmsi(sourcemmsi);

        List<PositionDto> positions = positionList.stream()
                .map(position -> conversionService.convert(position, PositionDto.class))
                .collect(Collectors.toList());

        log.info("Fetch all positions by sourcemmsi {} process end", sourcemmsi);

        return positions;
    }

    @Override
    public List<PositionDto> fetchByShipFlag(Flag shipFlag) {

        log.info("Fetch all ships by flag {} process begins", shipFlag.code());

        List<Position> positionList = positionRepository.findPositionByShipFlag(shipFlag.code());

        List<PositionDto> positions = positionList.stream()
                .map(position -> conversionService.convert(position, PositionDto.class))
                .collect(Collectors.toList());

        log.info("Fetch all ships by flag {} process end", shipFlag.code());

        return positions;
    }
}
