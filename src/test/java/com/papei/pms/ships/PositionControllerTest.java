package com.papei.pms.ships;

import com.papei.pms.ships.dto.ComplexRequestDto;
import com.papei.pms.ships.dto.CoordinateDto;
import com.papei.pms.ships.dto.PositionInsideBoxDto;
import com.papei.pms.ships.enums.Flag;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = {"spring.application.name=PositionControllerTest",
        "spring.jmx.default-domain=PositionControllerTest"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PositionControllerTest extends BasicWiremockTest {

    private final Integer SOURCEMMSI_ONE = 228051000;
    private final Integer SOURCEMMSI_TWO = 245257000;
    private final Double VALUE = 12.0;
    private final Flag SHIP_FLAG = Flag.NETHERLANDS;
    private final Double LONGITUDE = -4.4657183;
    private final Double LATITUDE = 48.38249;
    private final Integer MAX_DISTANCE = 500;
    private final Integer MIN_DISTANCE = 0;
    private final Double RADIUS = 1.0;
    private final LocalDateTime DATE_TIME_FROM = LocalDateTime.ofInstant(Instant.ofEpochMilli(1443650404000L), ZoneId.systemDefault());
//            LocalDateTime.parse("2015-10-01T01:00:04");
    private final LocalDateTime DATE_TIME_TO = LocalDateTime.ofInstant(Instant.ofEpochMilli(1443680753000L), ZoneId.systemDefault());
        //LocalDateTime.parse("2015-10-01T09:25:53");
//    private final Long T = 1443680753L; //1443650404

    @Test
    public void a_findBySourcemmsi() throws Exception {
        this.mockMvc.perform(get("/positions/{sourcemmsi}",
                SOURCEMMSI_ONE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void b_findByShipFlag() throws Exception {
        this.mockMvc.perform(get("/positions/flag/{shipFlag}",
                SHIP_FLAG))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void c_findPositionsNearGivenPoint() throws Exception {
        this.mockMvc.perform(get("/positions/spatio-temporal/point/{longitude}/{latitude}/{maxDistance}/{minDistance}/{dateTimeFrom}/{dateTimeTo}",
                LONGITUDE, LATITUDE, MAX_DISTANCE, MIN_DISTANCE, DATE_TIME_FROM, DATE_TIME_TO))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void d_findPositionsNearGivenPoint() throws Exception {
        this.mockMvc.perform(get("/positions/spatial/point/{longitude}/{latitude}/{maxDistance}/{minDistance}",
                LONGITUDE, LATITUDE, MAX_DISTANCE, MIN_DISTANCE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void e_knn() throws Exception {
        this.mockMvc.perform(get("/positions/spatio-temporal/k-nn/{longitude}/{latitude}/{dateTimeFrom}/{dateTimeTo}?page=0&pageSize=10",
                LONGITUDE, LATITUDE, DATE_TIME_FROM, DATE_TIME_TO))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void f_knn() throws Exception {
        this.mockMvc.perform(get("/positions/spatial/k-nn/{longitude}/{latitude}?sortBy=t&sortDirection=ASC&page=0&pageSize=10",
                LONGITUDE, LATITUDE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void g_findPositionsWithinCertainRadius() throws Exception {
        this.mockMvc.perform(get("/positions/spatial/circle/{longitude}/{latitude}/{radius}",
                LONGITUDE, LATITUDE, RADIUS, DATE_TIME_FROM, DATE_TIME_TO))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void h_findPositionsInsideBox() throws Exception {

        PositionInsideBoxDto positionInsideBoxDto = PositionInsideBoxDto.builder()
                .coordinates1(Stream.of(-4.49544, 48.383663).collect(Collectors.toList()))
                .coordinates2(Stream.of(-3.49544, 48.383663).collect(Collectors.toList()))
                .coordinates3(Stream.of(-3.49544, 47.383663).collect(Collectors.toList()))
                .coordinates4(Stream.of(-4.49544, 47.383663).collect(Collectors.toList()))
                .dateTimeFrom(DATE_TIME_FROM)
                .dateTimeTo(DATE_TIME_TO)
                .build();

        this.mockMvc.perform(post("/positions/box")
                .content(asJsonString(positionInsideBoxDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void i_findPositionsInsideBox() throws Exception {

        PositionInsideBoxDto positionInsideBoxDto = PositionInsideBoxDto.builder()
                .coordinates1(Stream.of(-4.49544, 48.383663).collect(Collectors.toList()))
                .coordinates2(Stream.of(-3.49544, 48.383663).collect(Collectors.toList()))
                .coordinates3(Stream.of(-3.49544, 47.383663).collect(Collectors.toList()))
                .coordinates4(Stream.of(-4.49544, 47.383663).collect(Collectors.toList()))
                .build();

        this.mockMvc.perform(post("/positions/box")
                .content(asJsonString(positionInsideBoxDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void j_findDistanceJoin() throws Exception {
        this.mockMvc.perform(get("/positions/spatio-temporal/distance-join/{sourcemmsiOne}/{sourcemmsiTwo}/{value}/{dateTimeFrom}/{dateTimeTo}",
                SOURCEMMSI_ONE, SOURCEMMSI_TWO, VALUE, DATE_TIME_FROM, DATE_TIME_TO))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void k_findPositionsInsideBox() throws Exception {

        CoordinateDto coordinatesA = CoordinateDto.builder().lon(-4.49544).lat(48.383663).build();
        CoordinateDto coordinatesB = CoordinateDto.builder().lon(-3.49544).lat(48.383663).build();
        CoordinateDto coordinatesC = CoordinateDto.builder().lon(-3.49544).lat(47.383663).build();

        Long t = 1446472561L;

        ComplexRequestDto complexRequestDto = ComplexRequestDto.builder()
                .coordinatesA(coordinatesA)
                .coordinatesB(coordinatesB)
                .coordinatesC(coordinatesC)
                .t(t)
                .build();

        this.mockMvc.perform(post("/positions/complex")
                .content(asJsonString(complexRequestDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
