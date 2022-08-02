package ru.soft1.soft_shop_light.web;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.soft1.soft_shop_light.configuration.CustomProperties;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

public class AuthorizationUtil {

    public static RequestPostProcessor adminHttpBasic(CustomProperties properties) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(properties.getAdminName(),
                properties.getAdminPassword());
    }

    public static RequestPostProcessor adminAuth(CustomProperties properties) {
        return SecurityMockMvcRequestPostProcessors.authentication(new UsernamePasswordAuthenticationToken(properties.getAdminName(),
                properties.getAdminPassword()));
    }


}
