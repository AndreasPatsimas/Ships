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
@TestPropertySource(properties = {"spring.application.name=AnfrControllerTest",
        "spring.jmx.default-domain=AnfrControllerTest"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnfrControllerTest extends BasicWiremockTest {

    private final String ID = "5fe322603b0fb6017a83916f";

    @Test
    public void a_findById() throws Exception {
        this.mockMvc.perform(get("/anfrs/{id}",
                ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }
}
