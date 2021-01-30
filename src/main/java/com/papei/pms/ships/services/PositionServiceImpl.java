package com.papei.pms.ships.services;


import com.papei.pms.ships.domain.Position;
import com.papei.pms.ships.domain.PositionAggregation;
import com.papei.pms.ships.dto.CoordinateDto;
import com.papei.pms.ships.dto.PositionDto;
import com.papei.pms.ships.dto.PositionInsideBoxDto;
import com.papei.pms.ships.enums.Flag;
import com.papei.pms.ships.repositories.PositionRepository;
import com.papei.pms.ships.utils.MathCalculations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.*;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@Slf4j
public class PositionServiceImpl implements PositionService {

    private final Sort SORT_BY = Sort.by(Sort.Direction.ASC, "t");

    private PositionRepository positionRepository;

    private MongoTemplate mongoTemplate;

    private ConversionService conversionService;

    @Autowired
    public PositionServiceImpl(PositionRepository positionRepository,
                               MongoTemplate mongoTemplate,
                               ConversionService conversionService) {
        this.positionRepository = positionRepository;
        this.mongoTemplate = mongoTemplate;
        this.conversionService = conversionService;
    }

    @Override
    public List<PositionDto> fetchBySourcemmsi(Integer sourcemmsi) {

        log.info("Fetch all positions by sourcemmsi {} process begins", sourcemmsi);

        List<Position> positionList = positionRepository.findPositionByMmsi(sourcemmsi, SORT_BY);

        List<PositionDto> positions = convert(positionList);

        log.info("Fetch all positions by sourcemmsi {} process end", sourcemmsi);

        return positions;
    }

    @Override
    public List<List<CoordinateDto>> fetchDistanceJoin(Integer sourcemmsiOne,
                                                       Integer sourcemmsiTwo,
                                                       Double value,
                                                       LocalDateTime dateTimeFrom,
                                                       LocalDateTime dateTimeTo) {

        log.info("Distance join for mmsi_one: {} and mmsi_two: {} process begins", sourcemmsiOne, sourcemmsiTwo);

        List<List<CoordinateDto>> distanceJoin = new ArrayList<>();

        List<Position> positionsOne = positionRepository.findPositionByMmsiAndT(sourcemmsiOne,
                Date.from(dateTimeFrom.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000,
                Date.from(dateTimeTo.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000);

        List<Position> positionsTwo = positionRepository.findPositionByMmsiAndT(sourcemmsiTwo,
                Date.from(dateTimeFrom.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000,
                Date.from(dateTimeTo.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000);

        positionsOne.forEach(positionOne -> {

            List<Position> posTwo = positionsTwo.stream()
                    .filter(pos -> pos.getT().equals(positionOne.getT()))
                    .collect(Collectors.toList());

            if (!posTwo.isEmpty()){

                Position positionTwo = posTwo.get(0);

                if (MathCalculations.distance(positionOne.getLocation().getCoordinates().getLon(), positionOne.getLocation().getCoordinates().getLat(),
                        positionTwo.getLocation().getCoordinates().getLon(), positionTwo.getLocation().getCoordinates().getLat()) <= value)
                    distanceJoin.add(Stream
                            .of(CoordinateDto.builder().lon(positionOne.getLocation().getCoordinates().getLon())
                                            .lat(positionOne.getLocation().getCoordinates().getLat()).build(),
                                    CoordinateDto.builder().lon(positionTwo.getLocation().getCoordinates().getLon())
                                            .lat(positionTwo.getLocation().getCoordinates().getLat()).build())
                            .collect(Collectors.toList()));
            }
        });

        log.info("Distance join for mmsi_one: {} and mmsi_two: {} process end", sourcemmsiOne, sourcemmsiTwo);

        return distanceJoin;
    }

    @Override
    public List<List<CoordinateDto>> fetchSimilarTrajectories(Integer sourcemmsiOne, Integer sourcemmsiTwo, Double value, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        log.info("Similar Trajectories for mmsi_one: {} and mmsi_two: {} process begins", sourcemmsiOne, sourcemmsiTwo);

        List<List<CoordinateDto>> similarTrajectories = new ArrayList<>();

        List<Position> positionsOne = positionRepository.findPositionByMmsiAndT(sourcemmsiOne,
                Date.from(dateTimeFrom.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000,
                Date.from(dateTimeTo.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000);

        List<Position> positionsTwo = positionRepository.findPositionByMmsiAndT(sourcemmsiTwo,
                Date.from(dateTimeFrom.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000,
                Date.from(dateTimeTo.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000);

        positionsOne.forEach(positionOne -> {

            List<Position> posTwo = positionsTwo.stream()
                    .filter(pos -> pos.getT().equals(positionOne.getT()))
                    .collect(Collectors.toList());

            if (!posTwo.isEmpty()){

                Position positionTwo = posTwo.get(0);

                if (MathCalculations.distance(positionOne.getLocation().getCoordinates().getLon(), positionOne.getLocation().getCoordinates().getLat(),
                        positionTwo.getLocation().getCoordinates().getLon(), positionTwo.getLocation().getCoordinates().getLat()) == value)
                    similarTrajectories.add(Stream
                            .of(CoordinateDto.builder().lon(positionOne.getLocation().getCoordinates().getLon())
                                            .lat(positionOne.getLocation().getCoordinates().getLat()).build(),
                                    CoordinateDto.builder().lon(positionTwo.getLocation().getCoordinates().getLon())
                                            .lat(positionTwo.getLocation().getCoordinates().getLat()).build())
                            .collect(Collectors.toList()));
            }
        });

        log.info("Similar Trajectories for mmsi_one: {} and mmsi_two: {} process end", sourcemmsiOne, sourcemmsiTwo);

        return similarTrajectories;
    }

    @Override
    public List<PositionAggregation> fetchByShipFlagGroupBySourceMmsi(Flag shipFlag) {

        log.info("Fetch trajectories by flag, group by sourcemmsi {} process begins", shipFlag.code());

        MatchOperation matchStage = Aggregation.match(new Criteria("shipFlag").is(shipFlag.code()));

        SortOperation sortByTimeAsc = sort(Sort.by(Sort.Direction.ASC, "t"));

        GroupOperation locationGroupOperation = group("sourcemmsi")
                .push("location").as("locations");

        Aggregation aggregation = newAggregation(matchStage, sortByTimeAsc, locationGroupOperation);

        List<PositionAggregation> positions = mongoTemplate
                .aggregate(aggregation, "positions", PositionAggregation.class).getMappedResults();

        log.info("Fetch trajectories by flag, group by sourcemmsi {} process end", shipFlag.code());

        return positions;
    }

    @Override
    public List<PositionDto> fetchPositionsNearGivenPoint(Double longitude,
                                                          Double latitude,
                                                          Integer maxDistance,
                                                          Integer minDistance,
                                                          LocalDateTime dateTimeFrom,
                                                          LocalDateTime dateTimeTo) {

        log.info("Fetch all positions near to our point[{}, {}] process begins", longitude, latitude);

        List<Position> positionList = positionRepository
                .findPositionsNearGivenPoint(longitude, latitude, maxDistance, minDistance,
                        Date.from(dateTimeFrom.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000,
                        Date.from(dateTimeTo.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000,
                        SORT_BY);

        List<PositionDto> positions = convert(positionList);

        log.info("Fetch all positions near to our point[{}, {}] process end", longitude, latitude);

        return positions;
    }

    @Override
    public List<PositionDto> fetchPositionsNearGivenPoint(Double longitude, Double latitude, Integer maxDistance, Integer minDistance) {
        log.info("Fetch all positions near to our point[{}, {}] process begins", longitude, latitude);

        List<Position> positionList = positionRepository
                .findPositionsNearGivenPoint(longitude, latitude, maxDistance, minDistance, SORT_BY);

        List<PositionDto> positions = convert(positionList);

        log.info("Fetch all positions near to our point[{}, {}] process end", longitude, latitude);

        return positions;
    }

    @Override
    public Page<PositionDto> knn(Double longitude,
                                 Double latitude,
                                 LocalDateTime dateTimeFrom,
                                 LocalDateTime dateTimeTo,
                                 Pageable pageable) {

        log.info("K-nn near to our point[{}, {}] process begins", longitude, latitude);

        Page<Position> positions = positionRepository.knn(longitude, latitude,
                Date.from(dateTimeFrom.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000,
                Date.from(dateTimeTo.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000,
                pageable);

        return getPositionDtosPage(pageable, positions);
    }

    @Override
    public Page<PositionDto> knn(Double longitude, Double latitude, Pageable pageable) {
        log.info("K-nn near to our point[{}, {}] process begins", longitude, latitude);

        Page<Position> positions = positionRepository.knn(longitude, latitude, pageable);

        return getPositionDtosPage(pageable, positions);
    }

    @Override
    public List<PositionDto> fetchPositionsWithinCertainRadius(Double longitude,
                                                               Double latitude,
                                                               Double radius,
                                                               LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {

        log.info("Fetch all positions around center[{}, {}] with radius: {} process begins", longitude, latitude, radius);

        Point center = new Point(longitude, latitude);
        Distance radiusCircle = new Distance(radius, Metrics.KILOMETERS);
        Circle circle = new Circle(center, radiusCircle);

        List<Position> positionList = positionRepository.findByLocationWithinAndTBetween(circle,
                Date.from(dateTimeFrom.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000,
                Date.from(dateTimeTo.atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000);

        List<PositionDto> positions = convert(positionList);

        log.info("Fetch all positions around center[{}, {}] with radius: {} process end", longitude, latitude, radius);

        return positions;
    }

    @Override
    public List<PositionDto> fetchPositionsWithinCertainRadius(Double longitude, Double latitude, Double radius) {
        log.info("Fetch all positions around center[{}, {}] with radius: {} process begins", longitude, latitude, radius);

        Point center = new Point(longitude, latitude);
        Distance radiusCircle = new Distance(radius, Metrics.KILOMETERS);
        Circle circle = new Circle(center, radiusCircle);

        List<Position> positionList = positionRepository.findByLocationWithin(circle);

        List<PositionDto> positions = convert(positionList);

        log.info("Fetch all positions around center[{}, {}] with radius: {} process end", longitude, latitude, radius);

        return positions;
    }

    @Override
    public List<PositionDto> fetchPositionsInsideBox(PositionInsideBoxDto positionInsideBoxDto) {

        log.info("Fetch all positions inside box process begins");

        List<Position> positionList;

        if (!ObjectUtils.isEmpty(positionInsideBoxDto.getDateTimeFrom()) && !ObjectUtils.isEmpty(positionInsideBoxDto.getDateTimeTo()))
            positionList = positionRepository.findPositionsInsideBox(positionInsideBoxDto.getCoordinates1(),
                    positionInsideBoxDto.getCoordinates2(), positionInsideBoxDto.getCoordinates3(),
                    positionInsideBoxDto.getCoordinates4(), positionInsideBoxDto.getCoordinates1(),
                    Date.from(positionInsideBoxDto.getDateTimeFrom().atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000,
                    Date.from(positionInsideBoxDto.getDateTimeTo().atZone(ZoneId.systemDefault()).toInstant()).getTime() / 1000);
        else
            positionList = positionRepository.findPositionsInsideBox(positionInsideBoxDto.getCoordinates1(),
                    positionInsideBoxDto.getCoordinates2(), positionInsideBoxDto.getCoordinates3(),
                    positionInsideBoxDto.getCoordinates4(), positionInsideBoxDto.getCoordinates1());

        List<PositionDto> positions = convert(positionList);

        log.info("Fetch all positions inside box process end");

        return positions;
    }

    @Override
    public Set<Integer> complexQuery(CoordinateDto coordinatesA,
                                     CoordinateDto coordinatesB,
                                     CoordinateDto coordinatesC,
                                     Long t) {

        log.info("Complex query process begins");

        List<Position> positionsA = positionRepository.complex(coordinatesA.getLon(), coordinatesA.getLat());

        Set<Integer> sourcemmsisA = positionsA.stream().map(Position::getSourcemmsi).collect(toSet());

        List<Position> positionsB = positionRepository
                .complexWithT(coordinatesB.getLon(), coordinatesB.getLat(), 3600L * 6L);

        Set<Integer> sourcemmsisB = positionsB.stream().map(Position::getSourcemmsi).collect(toSet());

        Set<Integer> intersectionAB = intersection(sourcemmsisA, sourcemmsisB);

        List<Position> positionsC = positionRepository
                .complexWithT(coordinatesC.getLon(), coordinatesC.getLat(), t);

        Set<Integer> sourcemmsisC = positionsC.stream().map(Position::getSourcemmsi).collect(toSet());

        Set<Integer> sourcemmsis = intersection(intersectionAB, sourcemmsisC);

        log.info("Complex query process end");

        return sourcemmsis;
    }

    private Set<Integer> intersection(Set<Integer> a, Set<Integer> b) {
        // unnecessary; just an optimization to iterate over the smaller set
        if (a.size() > b.size()) {
            return intersection(b, a);
        }

        Set<Integer> results = new HashSet<>();

        for (Integer element : a) {
            if (b.contains(element)) {
                results.add(element);
            }
        }

        return results;
    }

    private Page<PositionDto> getPositionDtosPage(Pageable pageable, Page<Position> positions){

        Page<PositionDto> positionDtosPage;

        Long total;
        if (ObjectUtils.isEmpty(positions)) {
            total = 0L;
            positionDtosPage = buildPagedResults(Collections.EMPTY_LIST, total, pageable);

        } else {

            List<PositionDto> positionDtos = new ArrayList<>(positions.getContent().size());
            positions.forEach(position -> positionDtos.add(conversionService.convert(position, PositionDto.class)));
            total = positions.getTotalElements();
            positionDtosPage = buildPagedResults(positionDtos, total, pageable);
        }

        log.info("Find all positions end. Total positions: {}, Page size {}, Page {} of {} pages.",
                total, pageable.getPageSize(), pageable.getPageNumber(), positions.getTotalPages());

        return positionDtosPage;
    }

    private <T> Page<T> buildPagedResults(List<T> list, Long countResult, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        return new PageImpl<>
                (list, PageRequest.of(pageable.getPageNumber(), pageSize), countResult);
    }

    private List<PositionDto> convert(List<Position> positionList){

        return positionList.stream()
                .map(position -> conversionService.convert(position, PositionDto.class))
                .collect(Collectors.toList());
    }
}
