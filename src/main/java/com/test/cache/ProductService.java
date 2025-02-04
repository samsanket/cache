package com.test.cache;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ExpiringCache<Long, Product> cache = new ExpiringCache<>(60); // Cache for 60 seconds

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long id) {
        Product cachedProduct = cache.get(id);
        if (cachedProduct != null) {
            System.out.println("Cache Hit: " + id);
            return cachedProduct;
        }

        System.out.println("Cache Miss: Fetching from DB...");
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        cache.put(savedProduct.getId(), savedProduct, 60);
        return savedProduct;
    }

    public Product updateProduct(Long id, Product newProduct) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(newProduct.getName());
                    existingProduct.setPrice(newProduct.getPrice());
                    Product updated = productRepository.save(existingProduct);
                    cache.put(id, updated, 60);
                    return updated;
                }).orElse(null);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
        cache.remove(id);
    }
}
