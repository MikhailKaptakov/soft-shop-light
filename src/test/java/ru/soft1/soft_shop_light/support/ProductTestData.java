package ru.soft1.soft_shop_light.support;

import ru.soft1.soft_shop_light.model.Product;
import java.util.List;

public class ProductTestData {
    public static final MatcherFactory.Matcher<Product> PRODUCT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Product.class);

    public static final long FIRST_ID = 1;

    private static final Product product = new Product(FIRST_ID, "prod1", "vendor1",
            "country1", "33 minutes", "description",
            1000, 5, true, true, true, false);
    private static final Product product2 = new Product(FIRST_ID+1, "prod2", "vendor2",
            "country2", "33 years", "undescription",
            5, 55, true, false, true, true);
    private static final Product product3 = new Product(FIRST_ID+2, "prod3", "vendor3",
            "country3", "5 seconds", "useless program",
            100000, 5, false, true, true, false);
    private static final Product product4 = new Product(FIRST_ID+3, "prod4", "vendor3",
            "country3", "5 seconds", "useless program",
            1000, 5, false, false, true, false);
    private static final Product product5 = new Product(FIRST_ID+4, "prod5", "vendor3",
            "country3", "5 seconds", "useless program",
            10000, 5, false, true, false, true);
    private static final Product newProduct = new Product("new Prod", "new Vendor",
            "russia", "unlimited", "do something",
            100000000, 3000, false, false, true, false);

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

    public static Product getNewProduct() {
        return new Product(newProduct);
    }

    public static List<Product> getAllProduct() {
        return List.of(getProductOne(), getProductTwo(), getProductThree(), getProductFour(), getProductFive());
    }

    public static List<Product> getAllAvailableProduct() {
        return List.of(getProductOne(), getProductTwo(), getProductThree(), getProductFour());
    }
}
