package com.papei.pms.ships.services;

import com.papei.pms.ships.domain.Polygon;
import com.papei.pms.ships.dto.PolygonRequestDto;
import com.papei.pms.ships.repositories.PolygonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class PolygonServiceImpl implements PolygonService {

    private PolygonRepository polygonRepository;

    private ConversionService conversionService;

    @Autowired
    public PolygonServiceImpl(PolygonRepository polygonRepository, ConversionService conversionService) {
        this.polygonRepository = polygonRepository;
        this.conversionService = conversionService;
    }

    @Override
    public void savePolygon(PolygonRequestDto polygonRequestDto) {

        log.info("Save polygon {} process begins", polygonRequestDto.getName());

        Polygon polygon = conversionService.convert(polygonRequestDto, Polygon.class);

        polygonRepository.save(polygon);

        log.info("Save polygon {} process end", polygonRequestDto.getName());
    }

    @Override
    public boolean checkIfShipExistsInsidePolygon(Double longitude,
                                                  Double latitude,
                                                  LocalDateTime dateTimeFrom,
                                                  LocalDateTime dateTimeTo) {

        log.info("Check if ship with coordinates: [{}, {}] exists inside polygon process begins", longitude, latitude);

        Optional<Polygon> polygon = polygonRepository.checkIfShipExistsInsidePolygon(longitude, latitude,
                Date.from(dateTimeFrom.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000,
                Date.from(dateTimeTo.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000);

        if (polygon.isPresent())
            return true;

        log.info("Check if ship with coordinates: [{}, {}] exists inside polygon process end", longitude, latitude);

        return false;
    }
}
