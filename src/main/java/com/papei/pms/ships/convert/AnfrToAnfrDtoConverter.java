package com.papei.pms.ships.convert;

import com.papei.pms.ships.domain.Anfr;
import com.papei.pms.ships.dto.AnfrDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AnfrToAnfrDtoConverter implements Converter<Anfr, AnfrDto> {

    @Override
    public AnfrDto convert(Anfr anfr) {
        return AnfrDto.builder()
                .id(anfr.getId())
                .maritimeArea(anfr.getMaritime_area())
                .registrationNumber(anfr.getRegistration_number())
                .imoNumber(anfr.getImo_number())
                .shipName(anfr.getShip_name())
                .callSign(anfr.getCallsign())
                .mmsi(anfr.getMmsi())
                .shipType(anfr.getShiptype())
                .length(anfr.getLength())
                .tonnage(anfr.getTonnage())
                .tonnageUnit(anfr.getTonnage_unit())
                .materielOnBoard(anfr.getMateriel_onboard())
                .atisCode(anfr.getAtis_code())
                .radioLicenseStatus(anfr.getRadio_license_status())
                .dateFirstLicense(anfr.getDate_first_license())
                .dateInactivityLicense(anfr.getDate_inactivity_license())
                .build();
    }
}
