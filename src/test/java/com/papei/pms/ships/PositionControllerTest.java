package com.papei.pms.ships;

import com.papei.pms.ships.enums.Flag;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
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
}
