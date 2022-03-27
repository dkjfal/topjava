package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Polik on 3/27/2022
 */
class ResourceControllerTest extends AbstractControllerTest {
    @Test
    void checkResources() throws Exception {
        perform(get("/resources/css/style.css"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.parseMediaType("text/css;charset=UTF-8")))
                .andExpect(status().isOk());
    }
}
