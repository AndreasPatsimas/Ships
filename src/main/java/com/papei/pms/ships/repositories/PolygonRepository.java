package com.papei.pms.ships.repositories;

import com.papei.pms.ships.domain.Polygon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface PolygonRepository extends MongoRepository<Polygon, String> {

    @Query(value = "{area: {$geoIntersects: {$geometry: {type: 'Point', coordinates: [?0, ?1]}}}}")
    Optional<Polygon> checkIfShipExistsInsidePolygon(Double longitude, Double latitude);
}
