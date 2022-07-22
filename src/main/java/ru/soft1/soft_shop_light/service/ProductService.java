package ru.soft1.soft_shop_light.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Cacheable("available_products")
    public List<Product> getAllAvailable() {
        return productRepository.getAllAvailable();
    }

    public Product getAvailable(long id) {
        Product product = ValidationUtil.checkNotFoundWithId(productRepository.get(id), id);
        ValidationUtil.checkNotFoundWithId(product.isAvailable(), id);
        return product;
    }

    @Transactional
    @CacheEvict(value = {"products", "available_products"}, allEntries = true)
    public void delete(long id) {
        ValidationUtil.checkNotFoundWithId(productRepository.delete(id), id);
    }

    @CacheEvict(value = {"products", "available_products"}, allEntries = true)
    public void update(Product product) {
        Assert.notNull(product, "product must not be null");
        productRepository.save(product);
    }

    @CacheEvict(value = "products", allEntries = true)
    public Product create(Product product) {
        Assert.notNull(product, "product must not be null");
        return productRepository.save(product);
    }

    @Transactional
    @CacheEvict(value = {"products", "available_products"}, allEntries = true)
    public void setAvailable(long id, boolean available) {
        Product product = get(id);
        product.setAvailable(available);
        productRepository.save(product);
    }
}
