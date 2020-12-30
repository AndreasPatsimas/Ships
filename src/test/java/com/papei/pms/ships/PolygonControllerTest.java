package com.papei.pms.ships;

import com.papei.pms.ships.dto.PolygonRequestDto;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
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
public class PolygonControllerTest extends BasicWiremockTest {

    private final Double LONGITUDE = 23.727789;
    private final Double LATITUDE = 37.9639439;
    private final LocalDateTime DATE_TIME = LocalDateTime.parse("2015-10-01T09:25:52");
//    private final Long T = 1443680752L;

    @Ignore
    @Test
    public void a_savePolygon() throws Exception {

        PolygonRequestDto polygonRequestDto = PolygonRequestDto.builder()
                .name("Adulthood")
                .p1(Stream.of(23.7517563, 37.9591876).collect(Collectors.toList()))
                .p2(Stream.of(23.7263221, 37.9562363).collect(Collectors.toList()))
                .p3(Stream.of(23.7252362, 37.9707717).collect(Collectors.toList()))
                .p4(Stream.of(23.7613592, 37.976186).collect(Collectors.toList()))
                .dateTime(DATE_TIME)
                .build();

        this.mockMvc.perform(post("/areas")
                .content(asJsonString(polygonRequestDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void b_checkIfShipExistsInsidePolygon() throws Exception {
        this.mockMvc.perform(get("/areas/{longitude}/{latitude}/{dateTime}",
                LONGITUDE, LATITUDE, DATE_TIME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }
}
