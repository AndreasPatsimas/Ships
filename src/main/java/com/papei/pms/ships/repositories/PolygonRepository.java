package com.papei.pms.ships.repositories;

import com.papei.pms.ships.domain.Polygon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface PolygonRepository extends MongoRepository<Polygon, String> {

    @Query(value = "{'$and':[{area: {$geoIntersects: {$geometry: {type: 'Point', coordinates: [?0, ?1]}}}}, " +
            "{t: {$gt: ?2}}, {t: {$lt: ?3}}]}")
    Optional<Polygon> checkIfShipExistsInsidePolygon(Double longitude, Double latitude, Long t1, Long t2);

    @Query(value = "{'$and':[{area: {$geoIntersects: {$geometry: {type: 'Point', coordinates: [?0, ?1]}}}}]}")
    Optional<Polygon> checkIfShipExistsInsidePolygon(Double longitude, Double latitude);
}
