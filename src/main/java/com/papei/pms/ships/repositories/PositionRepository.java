package com.papei.pms.ships.repositories;

import com.papei.pms.ships.domain.Position;
import org.springframework.data.geo.Circle;
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

    @Query(value = "{'$and':[{location:{$near:{$geometry:{type:'Point',coordinates:[?0 , ?1]},$maxDistance:?2, $minDistance: ?3}}}, " +
            "{t: {$gt: ?4}}, {t: {$lt: ?5}}]}")
    List<Position> findPositionsNearGivenPoint(Double longitude,
                                               Double latitude,
                                               Integer maxDistance,
                                               Integer minDistance,
                                               Long t1,
                                               Long t2);

//    @Query(value = "{'$and': [{location: {$geoWithin: {$centerSphere: [[?0, ?1], ?2 / 6378.1]}}}, {t: ?3}]}")
//    List<Position> findPositionsWithinCertainRadius(Double longitude, Double latitude, Integer radius, Long t);

    List<Position> findByLocationWithinAndTBetween(Circle circle, Long t1, Long t2);
}
