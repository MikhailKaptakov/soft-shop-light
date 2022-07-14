package ru.soft1.soft_shop_light.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class RootControllerTest extends AbstractControllerTest{

    @Test
    void root() throws Exception {
        perform(get("/"))
                .andExpect(forwardedUrl("/products"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}