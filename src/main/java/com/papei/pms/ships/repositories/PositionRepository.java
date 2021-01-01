package com.papei.pms.ships.repositories;

import com.papei.pms.ships.domain.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends MongoRepository<Position, String> {

    @Query(value = "{ 'sourcemmsi' : ?0 }")
    List<Position> findPositionByMmsi(Integer mmsi, Sort sort);

    @Query(value = "{$and: [{sourcemmsi: ?0},{t: {$gt: ?1}}, {t: {$lt: ?2}}]}")
    List<Position> findPositionByMmsiAndT(Integer mmsi, Long t1, Long t2);

    @Query(value = "{ 'shipFlag' : ?0 }")
    List<Position> findPositionByShipFlag(String shipFlag, Sort sort);

    @Query(value = "{'$and':[{location:{$near:{$geometry:{type:'Point',coordinates:[?0 , ?1]},$maxDistance:?2, $minDistance: ?3}}}, " +
            "{t: {$gt: ?4}}, {t: {$lt: ?5}}]}")
    List<Position> findPositionsNearGivenPoint(Double longitude,
                                               Double latitude,
                                               Integer maxDistance,
                                               Integer minDistance,
                                               Long t1,
                                               Long t2,
                                               Sort sort);

    @Query(value = "{'$and':[{location:{$near:{$geometry:{type:'Point', coordinates:[?0, ?1]}}}}, " +
            "{t: {$gt: ?2}}, {t: {$lt: ?3}}]}")
    Page<Position> knn(Double longitude, Double latitude, Long t1, Long t2, Pageable pageable);

    @Query(value = "{'$and':[{location:{$geoWithin:{$geometry:{type:'Polygon',coordinates :[[?0, ?1, ?2, ?3, ?4]]}}}}, " +
            "{t: {$gt: ?5}}, {t: {$lt: ?6}}]}")
    List<Position> findPositionsInsideBox(List<Double> coordinates1,
                                          List<Double> coordinates2,
                                          List<Double> coordinates3,
                                          List<Double> coordinates4,
                                          List<Double> coordinatesClose,
                                          Long t1,
                                          Long t2);

//    @Query(value = "{'$and': [{location: {$geoWithin: {$centerSphere: [[?0, ?1], ?2 / 6378.1]}}}, {t: ?3}]}")
//    List<Position> findPositionsWithinCertainRadius(Double longitude, Double latitude, Integer radius, Long t);

    List<Position> findByLocationWithinAndTBetween(Circle circle, Long t1, Long t2);
}
