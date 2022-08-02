package ru.soft1.soft_shop_light.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;

@Component
@ConfigurationProperties(prefix="custom.config")
@Data
@Validated
public class CustomProperties {

    @Email
    private String recipientEmail = "test@test.ru";

    private String adminName = "Andrey";

    private String adminPassword = "1";

}
