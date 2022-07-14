package ru.soft1.soft_shop_light.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.soft1.soft_shop_light.model.Product;

import java.util.List;

@Transactional(readOnly = true)
@Repository
public interface DataJpaProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllOrderById();

    @Modifying
    @Transactional
    @Query("DELETE FROM product p WHERE p.id=:id")
    long delete(@Param("id") long id);

}
