package ru.soft1.soft_shop_light.support;

import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.support.MatcherFactory;

import java.util.List;

public class ProductTestData {
    public static final MatcherFactory.Matcher<Product> PRODUCT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Product.class);

    public static final long FIRST_ID = 1;

    public static final Product product = new Product(1, "prod1", "vendor1",
            "country1", "33 minutes", "description",
            1000, 5, true, true);
    public static final Product product2 = new Product(2, "prod2", "vendor2",
            "country2", "33 years", "undescription",
            5, 55, true, false);
    public static final Product product3 = new Product(3, "prod3", "vendor3",
            "country3", "5 seconds", "useless program",
            100000, 5, false, true);
    public static final Product product4 = new Product(4, "prod4", "vendor3",
            "country3", "5 seconds", "useless program",
            1000, 5, false, false);
    public static final Product product5 = new Product(5, "prod5", "vendor3",
            "country3", "5 seconds", "useless program",
            10000, 5, false, true);

    public static Product getProductOne() {
        return new Product(product);
    }

    public static Product getProductTwo() {
        return new Product(product2);
    }

    public static Product getProductThree() {
        return new Product(product3);
    }

    public static Product getProductFour() {
        return new Product(product4);
    }

    public static Product getProductFive() {
        return new Product(product5);
    }

    public static List<Product> getAllProduct() {
        return List.of(getProductOne(), getProductTwo(), getProductThree(), getProductFour(), getProductFive());
    }
}
