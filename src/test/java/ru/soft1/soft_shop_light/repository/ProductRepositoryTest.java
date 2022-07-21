package ru.soft1.soft_shop_light.repository;

import org.attoparser.ParsingDocTypeMarkupUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.support.ProductTestData;
import ru.soft1.soft_shop_light.support.TimingExtension;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static ru.soft1.soft_shop_light.support.ProductTestData.getAllProduct;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(TimingExtension.class)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void getAllOrderById() {
        ProductTestData.PRODUCT_MATCHER.assertMatch(productRepository.getAllOrderById(), getAllProduct());
    }

    @Test
    void getAllBySort() {
        List<Product> expected = new ArrayList<>(ProductTestData.getAllProduct());
        expected.sort(Comparator.comparing(Product::getName));
        ProductTestData.PRODUCT_MATCHER.assertMatch(
                productRepository.getAllBySort(Sort.by(Sort.Direction.ASC, "name")), expected);
    }

    @Test
    void save() {
        Product expected = ProductTestData.getNewProduct();
        Product actual = productRepository.save(expected);
        expected.setId((long)Product.START_SEQ);
        ProductTestData.PRODUCT_MATCHER.assertMatch(
                actual, expected);
    }

    @Test
    void illegalSave() {
        Product product = ProductTestData.getNewProduct();
        product.setName("");
        Assertions.assertThrows(Exception.class,()-> productRepository.save(product));
    }

    @Test
    void delete() {
        //метод delete использовать очень аккуратно
        Assertions.assertTrue(productRepository.delete(ProductTestData.FIRST_ID + 3));
        Assertions.assertNull(productRepository.get(ProductTestData.FIRST_ID + 3));
    }

    @Test
    void get() {
        ProductTestData.PRODUCT_MATCHER.assertMatch(productRepository.get(ProductTestData.FIRST_ID),
                ProductTestData.getProductOne());
    }

    @Test
    void getAllAvailable() {
        ProductTestData.PRODUCT_MATCHER.assertMatch(productRepository.getAllAvailable(),
                ProductTestData.getAllAvailableProduct());
    }
}