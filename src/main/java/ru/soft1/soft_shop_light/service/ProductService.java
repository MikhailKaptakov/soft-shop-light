package ru.soft1.soft_shop_light.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.repository.ProductRepository;
import ru.soft1.soft_shop_light.util.validation.ValidationUtil;

import java.util.List;

@Slf4j
@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Cacheable("products")
    public List<Product> getAll() {
        return productRepository.getAllOrderById();
    }

    public Product get(long id) {
        return ValidationUtil.checkNotFoundWithId(productRepository.get(id), id);
    }

    public void delete(long id) {
        ValidationUtil.checkNotFoundWithId(productRepository.delete(id), id);
    }

    @CacheEvict(value = "products", allEntries = true)
    public Product update(Product product) {
        Assert.notNull(product, "product must not be null");
        return productRepository.save(product);
    }

    @CacheEvict(value = "products", allEntries = true)
    public Product create(Product product) {
        Assert.notNull(product, "product must not be null");
        return productRepository.save(product);
    }

    //todo getAvailable
    //todo getAvailable(Sort)
}
