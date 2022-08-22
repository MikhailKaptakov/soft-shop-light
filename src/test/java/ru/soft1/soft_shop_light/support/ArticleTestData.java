package ru.soft1.soft_shop_light.support;

import ru.soft1.soft_shop_light.model.Article;
import ru.soft1.soft_shop_light.model.Product;

import java.util.List;

public class ArticleTestData {
    public static final MatcherFactory.Matcher<Article> ARTICLE_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Article.class);

    public static final long FIRST_ID = 1;

    private static final Article first = new Article(FIRST_ID,
            "Header", "Preview", "Text", true);

    public static Article getFirst() {
        return new Article(first);
    }

    public static List<Article> getAllProduct() {
        return null; /*List.of(getProductOne(), getProductTwo(), getProductThree(), getProductFour(), getProductFive());*/
    }

    public static List<Article> getAllAvailableProduct() {
        return null;/*List.of(getProductOne(), getProductTwo(), getProductThree(), getProductFour());*/
    }
}
