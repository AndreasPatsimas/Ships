package com.papei.pms.ships.repositories;

import com.papei.pms.ships.domain.Anfr;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnfrRepository extends MongoRepository<Anfr, String> {
}
