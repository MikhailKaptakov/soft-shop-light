package ru.soft1.soft_shop_light.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.soft1.soft_shop_light.model.Article;
import ru.soft1.soft_shop_light.service.ArticleService;
import ru.soft1.soft_shop_light.to.ArticlePreview;
import ru.soft1.soft_shop_light.to.OrderPositionList;
import ru.soft1.soft_shop_light.to.ProductOrderForm;
import ru.soft1.soft_shop_light.util.exception.NotFoundException;
import ru.soft1.soft_shop_light.util.validation.ValidationUtil;

import java.util.Map;


@Slf4j
@Controller
@RequestMapping(value = ArticleController.URL)
public class ArticleController {
    static final String URL = "/article";

    @Autowired
    private ArticleService articleService;

    @ModelAttribute("article")
    public Article getArticle() {
        return new Article();
    }


    @GetMapping("/{id}")
    public String openArticle(@PathVariable("id") long id, @ModelAttribute("article") Article article) {
        Article current = articleService.getAvailable(id);
        article.setHeader(current.getHeader());
        article.setText(current.getText());
        article.setLogo(current.getLogo());
        article.setImage(current.getImage());
        return "user-article";
    }

}
