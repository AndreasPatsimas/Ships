package com.papei.pms.ships.repositories;

import com.papei.pms.ships.domain.EuropeanVesselRegister;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EuropeanVesselRegisterRepository extends MongoRepository<EuropeanVesselRegister, String> {
}
