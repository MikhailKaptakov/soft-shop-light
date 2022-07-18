package ru.soft1.soft_shop_light.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.repository.crud.CrudProductRepository;

import java.util.List;

@Repository
public class ProductRepository {

    @Autowired
    CrudProductRepository crudProductRepository;

    public List<Product> getAllOrderById() {
        return crudProductRepository.findAllOrderedById();
    }

    public List<Product> getAllBySort(Sort sort) {
        return  crudProductRepository.findAll(sort).stream().toList();
    }

    public Product save(Product product) {
        return crudProductRepository.save(product);
    }

    public boolean delete(long id) {
        return crudProductRepository.delete(id) != 0;
    }

    public Product get(long id) {
        return crudProductRepository.findById(id).orElse(null);
    }

}
