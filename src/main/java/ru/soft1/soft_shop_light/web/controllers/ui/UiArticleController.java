package ru.soft1.soft_shop_light.web.controllers.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.soft1.soft_shop_light.model.Article;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.service.ArticleService;
import ru.soft1.soft_shop_light.service.ProductService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = UiArticleController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UiArticleController {
    static final String URL = "/ui/article";

    @Autowired
    private ArticleService articleService;

    @GetMapping("/{id}")
    public Article getAvailable(@PathVariable("id") long id) {
        log.info("get state");
        return articleService.getAvailable(id);
    }

    @GetMapping
    public List<Article> getAllAvailable() {
        log.info("get All available");
        return articleService.getAllAvailable();
    }
}
