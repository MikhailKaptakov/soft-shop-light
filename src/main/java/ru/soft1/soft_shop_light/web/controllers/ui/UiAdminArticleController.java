package ru.soft1.soft_shop_light.web.controllers.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.soft1.soft_shop_light.configuration.ValidationCustomer;
import ru.soft1.soft_shop_light.model.Article;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.service.ArticleService;
import ru.soft1.soft_shop_light.util.validation.ValidationUtil;
import java.util.List;

import static ru.soft1.soft_shop_light.util.validation.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequestMapping(value = UiAdminArticleController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UiAdminArticleController {

    static final String URL = "/admin/ui/article";

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public List<Article> getAll() {
        log.info("get all");
        return articleService.getAll();
    }

    @GetMapping("/{id}")
    public Article get(@PathVariable("id") long id) {
        log.info("get {}", id);
        return articleService.get(id);
    }

    @PostMapping(value = "/save")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveOrUpdate(@Validated(ValidationCustomer.Web.class) Article article) {
        if (article.itsNew()) {
            log.info("create {}", article);
            checkNew(article);
            articleService.save(article);
        } else {
            log.info("update {} with id={}", article, article.getId());
            ValidationUtil.assureIdConsistent(article, article.getId());
            articleService.save(article);
        }
    }

    @Transactional
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        log.info("delete {}", id);
        articleService.delete(id);
    }

    @PostMapping("/available/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setAvailable(@PathVariable long id, @RequestParam boolean available) {
        log.info(available ? "available {}" : "not available {}", id);
        articleService.setAvailable(id, available);
    }

    @PostMapping("/image/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setImage(@PathVariable long id, @RequestParam MultipartFile image) {
        articleService.saveImage(id, image);
    }

    @PostMapping("/image/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable long id) {
        articleService.deleteImage(id);
    }

    @PostMapping("/logo/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setLogo(@PathVariable long id, @RequestParam MultipartFile logo) {
        articleService.saveLogo(id, logo);
    }

    @PostMapping("/logo/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLogo(@PathVariable long id) {
        articleService.deleteLogo(id);
    }

}
