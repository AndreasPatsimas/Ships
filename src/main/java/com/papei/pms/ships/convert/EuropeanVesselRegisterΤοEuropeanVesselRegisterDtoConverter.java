package com.papei.pms.ships.convert;

import com.papei.pms.ships.domain.EuropeanVesselRegister;
import com.papei.pms.ships.dto.EuropeanVesselRegisterDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EuropeanVesselRegisterΤοEuropeanVesselRegisterDtoConverter
        implements Converter<EuropeanVesselRegister, EuropeanVesselRegisterDto> {

    @Override
    public EuropeanVesselRegisterDto convert(EuropeanVesselRegister europeanVesselRegister) {
        return EuropeanVesselRegisterDto.builder()
                .id(europeanVesselRegister.getId())
                .countrycode(europeanVesselRegister.getCountrycode())
                .cfr(europeanVesselRegister.getCfr())
                .eventcode(europeanVesselRegister.getEventcode())
                .eventstartdate(europeanVesselRegister.getEventstartdate())
                .eventenddate(europeanVesselRegister.getEventenddate())
                .licenseind(europeanVesselRegister.getLicenseind())
                .registrationnbr(europeanVesselRegister.getRegistrationnbr())
                .extmarking(europeanVesselRegister.getExtmarking())
                .vesselname(europeanVesselRegister.getVesselname())
                .portcode(europeanVesselRegister.getPortcode())
                .portname(europeanVesselRegister.getPortname())
                .ircscode(europeanVesselRegister.getIrcscode())
                .ircs(europeanVesselRegister.getIrcs())
                .vmscode(europeanVesselRegister.getVmscode())
                .gearmaincode(europeanVesselRegister.getGearmaincode())
                .gearseccode(europeanVesselRegister.getGearseccode())
                .loa(europeanVesselRegister.getLoa())
                .lbp(europeanVesselRegister.getLbp())
                .tonref(europeanVesselRegister.getTonref())
                .tongt(europeanVesselRegister.getTongt())
                .powermain(europeanVesselRegister.getPowermain())
                .poweraux(europeanVesselRegister.getPoweraux())
                .hullmaterial(europeanVesselRegister.getHullmaterial())
                .comyear(europeanVesselRegister.getComyear())
                .commonth(europeanVesselRegister.getCommonth())
                .comday(europeanVesselRegister.getComday())
                .segment(europeanVesselRegister.getSegment())
                .expcountry(europeanVesselRegister.getExpcountry())
                .exptype(europeanVesselRegister.getExptype())
                .publicaidcode(europeanVesselRegister.getPublicaidcode())
                .decisiondate(europeanVesselRegister.getDecisiondate())
                .decisionsegcode(europeanVesselRegister.getDecisionsegcode())
                .constructionyear(europeanVesselRegister.getConstructionyear())
                .constructionplace(europeanVesselRegister.getConstructionplace())
                .build();
    }
}
