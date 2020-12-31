package com.papei.pms.ships.services;

import com.papei.pms.ships.domain.EuropeanVesselRegister;
import com.papei.pms.ships.dto.EuropeanVesselRegisterDto;
import com.papei.pms.ships.repositories.EuropeanVesselRegisterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class EuropeanVesselRegisterServiceImpl implements EuropeanVesselRegisterService {

    private EuropeanVesselRegisterRepository europeanVesselRegisterRepository;

    private ConversionService conversionService;

    public EuropeanVesselRegisterServiceImpl(EuropeanVesselRegisterRepository europeanVesselRegisterRepository,
                                             ConversionService conversionService) {
        this.europeanVesselRegisterRepository = europeanVesselRegisterRepository;
        this.conversionService = conversionService;
    }

    @Override
    public EuropeanVesselRegisterDto fetchById(String id) {

        log.info("Fetch European Vessel [id:{}] process begins", id);

        Optional<EuropeanVesselRegister> optionalEuropeanVesselRegister = europeanVesselRegisterRepository.findById(id);

        EuropeanVesselRegisterDto europeanVesselRegisterDto = optionalEuropeanVesselRegister
                .map(europeanVesselRegister -> conversionService.convert(europeanVesselRegister,
                        EuropeanVesselRegisterDto.class))
                .orElse(null);

        log.info("Fetch European Vessel [id:{}] process end", id);

        return europeanVesselRegisterDto;
    }
}
