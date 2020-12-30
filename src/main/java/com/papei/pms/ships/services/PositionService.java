package com.papei.pms.ships.services;

import com.papei.pms.ships.dto.PositionDto;
import com.papei.pms.ships.enums.Flag;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface PositionService {

    List<PositionDto> fetchBySourcemmsi(Integer sourcemmsi);

    List<PositionDto> fetchByShipFlag(Flag shipFlag);

    List<PositionDto> fetchPositionsNearGivenPoint(Double longitude,
                                                   Double latitude,
                                                   Integer maxDistance,
                                                   Integer minDistance,
                                                   LocalDateTime dateTimeFrom,
                                                   LocalDateTime dateTimeTo);

    List<PositionDto> fetchPositionsWithinCertainRadius(Double longitude,
                                                        Double latitude,
                                                        Double radius,
                                                        LocalDateTime dateTimeFrom,
                                                        LocalDateTime dateTimeTo);
}
