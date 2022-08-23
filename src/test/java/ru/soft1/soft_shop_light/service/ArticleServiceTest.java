package ru.soft1.soft_shop_light.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.soft1.soft_shop_light.model.Article;
import ru.soft1.soft_shop_light.support.ArticleTestData;
import ru.soft1.soft_shop_light.support.TimingExtension;
import ru.soft1.soft_shop_light.util.exception.NotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(TimingExtension.class)
class ArticleServiceTest {


    @Autowired
    private ArticleService articleService;

    @Test
    void getAll() {
        List<Article> expected = ArticleTestData.getAll();
        ArticleTestData.ARTICLE_MATCHER.assertMatch(articleService.getAll(), expected);
    }

    @Test
    void getAllAvailable() {
        List<Article> expected = ArticleTestData.getAllAvailable();
        ArticleTestData.ARTICLE_MATCHER.assertMatch(articleService.getAllAvailable(), expected);
    }

    @Test
    void getAvailable() {
        Article expected = ArticleTestData.getFirst();
        ArticleTestData.ARTICLE_MATCHER.assertMatch(articleService.get(1), expected);
    }

    @Test
    void getAvailableNotAvailable() {
        assertThrows(NotFoundException.class, () -> articleService.getAvailable(3));
    }


    @Test
    void get() {
        Article expected = ArticleTestData.getThird();
        ArticleTestData.ARTICLE_MATCHER.assertMatch(articleService.get(3), expected);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> articleService.get(150));
    }

    @Test
    void setAvailable() {
        Article expected = ArticleTestData.getThird();
        expected.setAvailable(true);
        articleService.setAvailable(expected.getId(), true);
        ArticleTestData.ARTICLE_MATCHER.assertMatch(articleService.get(expected.getId()), expected);
    }

    @Test
    void create() {
        Article expected = ArticleTestData.getNew();
        Article actual = articleService.save(expected);
        expected.setId((long)Article.START_SEQ);
        ArticleTestData.ARTICLE_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void delete() {
        articleService.delete(ArticleTestData.FIRST_ID);
        assertThrows(NotFoundException.class, () -> articleService.get(ArticleTestData.FIRST_ID));
    }
}