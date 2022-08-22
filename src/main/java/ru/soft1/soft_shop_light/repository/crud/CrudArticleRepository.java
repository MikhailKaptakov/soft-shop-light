package ru.soft1.soft_shop_light.repository.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.soft1.soft_shop_light.model.Article;
import ru.soft1.soft_shop_light.model.Product;

import java.util.List;

@Transactional(readOnly = true)
@Repository
public interface CrudArticleRepository extends JpaRepository<Article, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Article a WHERE a.id=:id")
    int delete(@Param("id") long id);

    @Query("SELECT a FROM Article a ORDER BY a.id ASC")
    List<Article> findAllOrderedById();

    @Query("SELECT a FROM Article a WHERE a.available=TRUE ORDER BY p.id ASC")
    List<Article> findAllAvailableOrderedById();

}
