package ru.soft1.soft_shop_light.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.repository.datajpa.DataJpaProductRepository;

import java.util.List;

@Repository
public class ProductRepository {

    @Autowired
    DataJpaProductRepository dataJpaProductRepository;

    public List<Product> getAllOrderById() {
        return dataJpaProductRepository.findAllOrderedById();
    }

    public List<Product> getAllBySort(Sort sort) {
        return  dataJpaProductRepository.findAll(sort).stream().toList();
    }

    public Product save(Product product) {
        return dataJpaProductRepository.save(product);
    }

    public boolean delete(long id) {
        return dataJpaProductRepository.delete(id) != 0;
    }

    public Product get(long id) {
        return dataJpaProductRepository.findById(id).orElse(null);
    }

}
