package com.papei.pms.ships.services;

import com.papei.pms.ships.dto.EuropeanVesselRegisterDto;
import org.springframework.stereotype.Service;

@Service
public interface EuropeanVesselRegisterService {

    EuropeanVesselRegisterDto fetchById(String id);
}
