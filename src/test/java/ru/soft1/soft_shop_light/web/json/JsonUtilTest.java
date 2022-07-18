package ru.soft1.soft_shop_light.web.json;

import org.junit.jupiter.api.Test;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.support.ProductTestData;

import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


class JsonUtilTest {

    @Test
    void readWriteValue() {
        Product expected = ProductTestData.getProductOne();
        String json = JsonUtil.writeValue(expected);
        System.out.println(json);
        Product actual = JsonUtil.readValue(json, Product.class);
        ProductTestData.PRODUCT_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void readWriteValues() {
        List<Product> expected = List.of(ProductTestData.getProductOne(), ProductTestData.getProductTwo());
        String json = JsonUtil.writeValue(expected);
        System.out.println(json);
        List<Product> actual = JsonUtil.readValues(json, Product.class);
        ProductTestData.PRODUCT_MATCHER.assertMatch(actual, expected);
    }

   /*
    //todo after security
    @Test
    void writeOnlyAccess() {
        Product
        String json = JsonUtil.writeValue(user);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = jsonWithPassword(user, "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
*/
}