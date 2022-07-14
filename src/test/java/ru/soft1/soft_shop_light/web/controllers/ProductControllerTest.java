package ru.soft1.soft_shop_light.web.controllers;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends AbstractControllerTest{

    @Test
    void showAllProducts() throws Exception {
        perform(get("/products"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}