package ru.soft1.soft_shop_light.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.soft1.soft_shop_light.model.Article;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.support.ArticleTestData;
import ru.soft1.soft_shop_light.support.ProductTestData;
import ru.soft1.soft_shop_light.support.TimingExtension;

import static org.junit.jupiter.api.Assertions.*;
import static ru.soft1.soft_shop_light.support.ProductTestData.getAllProduct;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(TimingExtension.class)
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void getAllOrderById() {
        ArticleTestData.ARTICLE_MATCHER.assertMatch(articleRepository.getAllOrderById(), ArticleTestData.getAll());
    }

    @Test
    void save() {
        Article expected = ArticleTestData.getNew();
        Article actual = articleRepository.save(expected);
        expected.setId((long)Product.START_SEQ);
        ArticleTestData.ARTICLE_MATCHER.assertMatch(
                actual, expected);
    }

    @Test
    void illegalSave() {
        Article article = ArticleTestData.getNew();
        article.setHeader("");
        Assertions.assertThrows(Exception.class,()-> articleRepository.save(article));
    }

    @Test
    void delete() {
        Assertions.assertTrue(articleRepository.delete(ArticleTestData.FIRST_ID));
        Assertions.assertNull(articleRepository.get(ArticleTestData.FIRST_ID));
    }

    @Test
    void get() {
        ArticleTestData.ARTICLE_MATCHER.assertMatch(articleRepository.get(ArticleTestData.FIRST_ID),
                ArticleTestData.getFirst());
    }

    @Test
    void getAllAvailable() {
        ArticleTestData.ARTICLE_MATCHER.assertMatch(articleRepository.getAllAvailable(),
                ArticleTestData.getAllAvailable());
    }
}