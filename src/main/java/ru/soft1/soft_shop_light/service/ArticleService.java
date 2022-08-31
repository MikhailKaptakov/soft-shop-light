package ru.soft1.soft_shop_light.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import ru.soft1.soft_shop_light.model.Article;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.repository.ArticleRepository;
import ru.soft1.soft_shop_light.util.validation.ValidationUtil;
import java.util.List;

import static ru.soft1.soft_shop_light.util.Util.toBytes;

@Slf4j
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> getAll() {
        return articleRepository.getAllOrderById();
    }

    @Cacheable("articles")
    public List<Article> getAllAvailable() {
        return articleRepository.getAllAvailable();
    }

    public Article get(long id) {
        return ValidationUtil.checkNotFoundWithId(articleRepository.get(id), id);
    }

    @Cacheable("articles")
    public Article getAvailable(long id) {
        Article article = ValidationUtil.checkNotFoundWithId(articleRepository.get(id), id);
        ValidationUtil.checkNotFoundWithId(article.isAvailable(), id);
        return article;
    }


    @Transactional
    @CacheEvict(value ="articles", allEntries = true)
    public void setAvailable(long id, boolean available) {
        Article article = articleRepository.get(id);
        article.setAvailable(available);
        articleRepository.save(article);
    }

    @CacheEvict(value = "articles", allEntries = true)
    public Article save(Article article) {
        Assert.notNull(article, "must not be null");
        return articleRepository.save(article);
    }

    @CacheEvict(value = "articles", allEntries = true)
    public Article saveNewByForm(Article article) {
        article.setAvailable(false);
        return save(article);
    }

    @CacheEvict(value = "articles", allEntries = true)
    public Article updateByForm(Article article) {
        Article fromRepo = articleRepository.get(article.getId());
        fromRepo.setHeader(article.getHeader());
        fromRepo.setText(article.getText());
        fromRepo.setPreview(article.getPreview());
        return save(fromRepo);
    }

    @Transactional
    @CacheEvict(value ="articles", allEntries = true)
    public void saveImage(long id, MultipartFile image) {
        Article article = get(id);
        article.setImage(toBytes(image));
        articleRepository.save(article);
    }

    @Transactional
    @CacheEvict(value ="articles", allEntries = true)
    public void deleteImage(long id) {
        Article article = get(id);
        article.setImage(null);
        articleRepository.save(article);
    }

    @Transactional
    @CacheEvict(value ="articles", allEntries = true)
    public void saveLogo(long id, MultipartFile image) {
        Article article = get(id);
        article.setLogo(toBytes(image));
        articleRepository.save(article);
    }

    @Transactional
    @CacheEvict(value ="articles", allEntries = true)
    public void deleteLogo(long id) {
        Article article = get(id);
        article.setLogo(null);
        articleRepository.save(article);
    }

    @Transactional
    @CacheEvict(value = "articles", allEntries = true)
    public void delete(long id) {
        ValidationUtil.checkNotFoundWithId(articleRepository.delete(id), id);
    }



}
