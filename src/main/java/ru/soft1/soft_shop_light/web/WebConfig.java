package ru.soft1.soft_shop_light.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/admin").setViewName("admin-products");
        registry.addViewController("/admin/articles").setViewName("admin-article");
        registry.addViewController("/products").setViewName("user-products");
        registry.addViewController("/about").setViewName("about");
        registry.addViewController("/index").setViewName("forward:/home");
        registry.addViewController("/home").setViewName("index");
    }
}
