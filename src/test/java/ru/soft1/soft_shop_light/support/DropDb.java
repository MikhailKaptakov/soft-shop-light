package ru.soft1.soft_shop_light.support;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

@SpringBootTest
@Sql(scripts = "classpath:db/dropDB_hsql.sql", config = @SqlConfig(encoding = "UTF-8"),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class DropDb {

    @Disabled //use if you need drop db
    @Test
    void drop() {
    }
}
