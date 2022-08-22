package ru.soft1.soft_shop_light.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.soft1.soft_shop_light.model.Article;
import ru.soft1.soft_shop_light.repository.crud.CrudArticleRepository;

import java.util.List;

@Repository
public class ArticleRepository {

    @Autowired
    CrudArticleRepository crudArticleRepository;

    public List<Article> getAllOrderById() {
        return crudArticleRepository.findAllOrderedById();
    }

    public List<Article> getAllBySort(Sort sort) {
        return  crudArticleRepository.findAll(sort).stream().toList();
    }

    @Transactional
    public Article save(Article article) {
        return crudArticleRepository.save(article);
    }

    @Transactional
    public boolean delete(long id) {
        return crudArticleRepository.delete(id) != 0;
    }

    public Article get(long id) {
        return crudArticleRepository.findById(id).orElse(null);
    }

    public List<Article> getAllAvailable() {
        return crudArticleRepository.findAllAvailableOrderedById();
    }
}
