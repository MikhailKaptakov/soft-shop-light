package ru.soft1.soft_shop_light.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.soft1.soft_shop_light.support.TimingExtension;
import ru.soft1.soft_shop_light.util.converters.EmailMessageFabricator;

import javax.annotation.PostConstruct;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(TimingExtension.class)
class OrderServiceTest {

}