package ru.soft1.soft_shop_light.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.repository.ProductRepository;
import ru.soft1.soft_shop_light.support.ProductTestData;
import ru.soft1.soft_shop_light.support.TimingExtension;
import ru.soft1.soft_shop_light.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(TimingExtension.class)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void getAll() {
        List<Product> expected = ProductTestData.getAllProduct();
        ProductTestData.PRODUCT_MATCHER.assertMatch(productService.getAll(), expected);
    }

    @Test
    void get() {
        Product expected = ProductTestData.getProductOne();
        ProductTestData.PRODUCT_MATCHER.assertMatch(productService.get(1), expected);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> productService.get(17));
    }

    @Test
    void delete() {
        productService.delete(ProductTestData.FIRST_ID + 3);
        Assertions.assertNull(productRepository.get(ProductTestData.FIRST_ID + 3));
    }

    @Test
    void update() {
        Product expected = ProductTestData.getProductOne();
        expected.setName("New name for product 1");
        productService.update(expected);
        ProductTestData.PRODUCT_MATCHER.assertMatch(productService.get(expected.getId()), expected);
    }

    @Test
    void create() {
        Product expected = ProductTestData.getNewProduct();
        Product actual = productService.create(expected);
        expected.setId((long)Product.START_SEQ);
        ProductTestData.PRODUCT_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void getAvailable() {
        List<Product> expected = ProductTestData.getAllAvailableProduct();
        ProductTestData.PRODUCT_MATCHER.assertMatch(productService.getAllAvailable(), expected);
    }

    @Test
    void setAvailable() {
        Product expected = ProductTestData.getProductFive();
        expected.setAvailable(true);
        productService.setAvailable(expected.getId(), true);
        ProductTestData.PRODUCT_MATCHER.assertMatch(productService.get(expected.getId()), expected);
    }

    @Test
    void setNds() {
        Product expected = ProductTestData.getProductFive();
        expected.setNdsInclude(true);
        productService.setNds(expected.getId(), true);
        ProductTestData.PRODUCT_MATCHER.assertMatch(productService.get(expected.getId()), expected);
    }

    @Test
    void setTechSupport() {
        Product expected = ProductTestData.getProductFive();
        expected.setRequiredTechnicalSupport(true);
        productService.setTechSupport(expected.getId(), true);
        ProductTestData.PRODUCT_MATCHER.assertMatch(productService.get(expected.getId()), expected);
    }
}