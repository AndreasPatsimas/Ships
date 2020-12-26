package com.papei.pms.ships.repositories;

import com.papei.pms.ships.domain.Position;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends MongoRepository<Position, String> {

    @Query(value = "{ 'sourcemmsi' : ?0 }")
    List<Position> findPositionByMmsi(Integer mmsi);

    @Query(value = "{ 'shipFlag' : ?0 }")
    List<Position> findPositionByShipFlag(String shipFlag);
}
