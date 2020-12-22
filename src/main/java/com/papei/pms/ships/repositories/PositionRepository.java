package com.papei.pms.ships.repositories;

import com.papei.pms.ships.domain.Position;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends MongoRepository<Position, String> {

    List<Position> findBySourcemmsi(Integer sourcemmsi);
}
