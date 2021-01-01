package com.papei.pms.ships;

import com.papei.pms.ships.dto.PositionInsideBoxDto;
import com.papei.pms.ships.enums.Flag;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
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

    private final Integer SOURCEMMSI = 228051000;
    private final Flag SHIP_FLAG = Flag.FRANCE;
    private final Double LONGITUDE = -4.4657183;
    private final Double LATITUDE = 48.38249;
    private final Integer MAX_DISTANCE = 500;
    private final Integer MIN_DISTANCE = 0;
    private final Double RADIUS = 1.0;
    private final LocalDateTime DATE_TIME_FROM = LocalDateTime.parse("2015-10-01T08:25:52");
    private final LocalDateTime DATE_TIME_TO = LocalDateTime.parse("2015-10-01T09:25:53");
//    private final Long T = 1443680752L; //1443650405

    @Test
    public void a_findBySourcemmsi() throws Exception {
        this.mockMvc.perform(get("/positions/{sourcemmsi}",
                SOURCEMMSI))
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
        this.mockMvc.perform(get("/positions/point/{longitude}/{latitude}/{maxDistance}/{minDistance}/{dateTimeFrom}/{dateTimeTo}",
                LONGITUDE, LATITUDE, MAX_DISTANCE, MIN_DISTANCE, DATE_TIME_FROM, DATE_TIME_TO))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void d_knn() throws Exception {
        this.mockMvc.perform(get("/positions/k-nn/{longitude}/{latitude}/{dateTimeFrom}/{dateTimeTo}?sortBy=t&sortDirection=ASC&page=0&pageSize=10",
                LONGITUDE, LATITUDE, DATE_TIME_FROM, DATE_TIME_TO))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void e_findPositionsWithinCertainRadius() throws Exception {
        this.mockMvc.perform(get("/positions/circle/{longitude}/{latitude}/{radius}/{dateTimeFrom}/{dateTimeTo}",
                LONGITUDE, LATITUDE, RADIUS, DATE_TIME_FROM, DATE_TIME_TO))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void f_findPositionsInsideBox() throws Exception {

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
}
