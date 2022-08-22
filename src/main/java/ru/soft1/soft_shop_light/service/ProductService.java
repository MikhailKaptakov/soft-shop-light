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
import ru.soft1.soft_shop_light.util.validation.ValidationUtil;
import java.util.List;

import static ru.soft1.soft_shop_light.util.Util.toBytes;

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

    public Product get(long id) {
        return ValidationUtil.checkNotFoundWithId(productRepository.get(id), id);
    }

    @Cacheable("products")
    public List<Product> getAllAvailable() {
        return productRepository.getAllAvailable();
    }

    @Cacheable("favorites")
    public List<Product> getAllFavoriteAvailable() {
        return productRepository.getAllFavoriteAvailable();
    }

    public Product getAvailable(long id) {
        Product product = ValidationUtil.checkNotFoundWithId(productRepository.get(id), id);
        ValidationUtil.checkNotFoundWithId(product.isAvailable(), id);
        return product;
    }

    @Transactional
    @CacheEvict(value = {"products", "favorites"}, allEntries = true)
    public void delete(long id) {
        ValidationUtil.checkNotFoundWithId(productRepository.delete(id), id);
    }

    @CacheEvict(value = {"products", "favorites"}, allEntries = true)
    public Product save(Product product) {
        Assert.notNull(product, "product must not be null");
        return productRepository.save(product);
    }

    @Transactional
    @CacheEvict(value ={"products", "favorites"}, allEntries = true)
    public void setAvailable(long id, boolean available) {
        Product product = get(id);
        product.setAvailable(available);
        productRepository.save(product);
    }

    @Transactional
    @CacheEvict(value ={"products", "favorites"}, allEntries = true)
    public void setNds(long id, boolean isNds) {
        Product product = get(id);
        product.setNdsInclude(isNds);
        productRepository.save(product);
    }

    @Transactional
    @CacheEvict(value ={"products", "favorites"}, allEntries = true)
    public void setTechSupport(long id, boolean isSupported) {
        Product product = get(id);
        product.setRequiredTechnicalSupport(isSupported);
        productRepository.save(product);
    }

    @Transactional
    @CacheEvict(value ={"products", "favorites"}, allEntries = true)
    public void saveImage(long id, MultipartFile image) {
        Product product = get(id);
        product.setImage(toBytes(image));
        productRepository.save(product);
    }

    @Transactional
    @CacheEvict(value ={"products", "favorites"}, allEntries = true)
    public void deleteImage(long id) {
        Product product = get(id);
        product.setImage(null);
        productRepository.save(product);
    }

    @Transactional
    @CacheEvict(value ={"products", "favorites"}, allEntries = true)
    public void setFavorite(long id, boolean favorite) {
        Product product = get(id);
        product.setFavorite(favorite);
        productRepository.save(product);
    }

}
