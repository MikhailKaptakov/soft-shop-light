package ru.soft1.soft_shop_light.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.repository.ProductRepository;
import ru.soft1.soft_shop_light.util.exception.ImageConversionException;
import ru.soft1.soft_shop_light.util.validation.ValidationUtil;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.getAllOrderById();
    }

    @Cacheable("products")
    public Product get(long id) {
        return ValidationUtil.checkNotFoundWithId(productRepository.get(id), id);
    }

    @Cacheable("products")
    public List<Product> getAllAvailable() {
        return productRepository.getAllAvailable();
    }

    @Cacheable("products")
    public Product getAvailable(long id) {
        Product product = ValidationUtil.checkNotFoundWithId(productRepository.get(id), id);
        ValidationUtil.checkNotFoundWithId(product.isAvailable(), id);
        return product;
    }

    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public void delete(long id) {
        ValidationUtil.checkNotFoundWithId(productRepository.delete(id), id);
    }

    @CacheEvict(value = "products", allEntries = true)
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
    @CacheEvict(value ="products", allEntries = true)
    public void setAvailable(long id, boolean available) {
        Product product = get(id);
        product.setAvailable(available);
        productRepository.save(product);
    }

    @Transactional
    @CacheEvict(value ="products", allEntries = true)
    public void setNds(long id, boolean isNds) {
        Product product = get(id);
        product.setNdsInclude(isNds);
        productRepository.save(product);
    }

    @Transactional
    @CacheEvict(value ="products", allEntries = true)
    public void setTechSupport(long id, boolean isSupported) {
        Product product = get(id);
        product.setRequiredTechnicalSupport(isSupported);
        productRepository.save(product);
    }

    @Transactional
    @CacheEvict(value ="products", allEntries = true)
    public void saveImage(long id, MultipartFile image) {
        Product product = get(id);
        product.setImage(toBytes(image));
        productRepository.save(product);
    }

    private byte[] toBytes(MultipartFile image) {
        try {
            return image.getBytes();
        } catch (IOException e) {
            throw new ImageConversionException("Не удалось преобразовать изображение");
        }
    }
}
