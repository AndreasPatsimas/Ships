package com.papei.pms.ships.services;

import com.papei.pms.ships.dto.PolygonRequestDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface PolygonService {

    void savePolygon(PolygonRequestDto polygonRequestDto);

    boolean checkIfShipExistsInsidePolygon(Double longitude, Double latitude, LocalDateTime dateTime);
}
