package com.papei.pms.ships;

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
@TestPropertySource(properties = {"spring.application.name=EuropeanVesselRegisterControllerTest",
        "spring.jmx.default-domain=EuropeanVesselRegisterControllerTest"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EuropeanVesselRegisterControllerTest extends BasicWiremockTest {

    private String ID = "5fec7ffe38a8b8cdf941cc18";

    @Test
    public void a_findEuropeanVessel() throws Exception {
        this.mockMvc.perform(get("/european-vessels/{id}",
                ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }
}
