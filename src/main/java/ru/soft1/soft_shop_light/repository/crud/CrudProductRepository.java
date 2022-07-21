package ru.soft1.soft_shop_light.repository.crud;

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
public interface CrudProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Product p WHERE p.id=:id")
    int delete(@Param("id") long id);

    @Query("SELECT p FROM Product p ORDER BY p.id ASC")
    List<Product> findAllOrderedById();

    @Query("SELECT p FROM Product p WHERE p.available=TRUE ORDER BY p.id ASC")
    List<Product> findAllAvailableOrderedById();
}
