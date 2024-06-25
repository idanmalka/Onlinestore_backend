package com.meirfadida.onlinestore.product;
import com.meirfadida.onlinestore.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductService {
    private final ProductTypeRepository productTypeRepository;
    private final Map<ProductType, ProductRepository<? extends Product>> repositoryMap;
    @Autowired
    private Environment env;
    public ProductService(ProductTypeRepository productTypeRepository,BookRepository bookRepository, ShirtRepository shirtRepository) {
        this.productTypeRepository = productTypeRepository;
        this.repositoryMap = new EnumMap<>(ProductType.class);
        this.repositoryMap.put(ProductType.Book, bookRepository);
        this.repositoryMap.put(ProductType.Shirt, shirtRepository);
    }

    public List<? extends Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        for (ProductRepository<? extends Product> repository : repositoryMap.values()) {
            products.addAll(repository.findAll());
        }
        return products;
    }

    public List<? extends Product> getAllProductsByType(ProductType type) {
        ProductRepository<? extends Product> repository = repositoryMap.get(type);
        if(repository == null) {
            throw new IllegalArgumentException("Invalid Product Type: " + type);
        }
        return repository.findAll();
    }

    public Product getProductById(Long productId) {
        ProductType productType = productTypeRepository.findProductTypeById(productId);
        if (productType == null) {
            throw new ProductNotFoundException("Product not found with id: " + productId);
        }

        ProductRepository<? extends Product> repository = repositoryMap.get(productType);
        if (repository == null) {
            throw new IllegalArgumentException("Unknown product type: " + productType);
        }

        return repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
    }

    public <T extends Product> T addProduct(T product) {
        ProductType productType = product.getType();
        ProductRepository<T> repository = (ProductRepository<T>) repositoryMap.get(productType);
        if (repository == null) {
            throw new IllegalArgumentException("Unknown product type: " + productType);
        }
        return repository.save(product);
    }

//    public <T extends Product> T updateProduct(T updatedProduct, Long productId) {
//        JpaRepository<T, Long> repository = (JpaRepository<T, Long>) repositoryMap.get(updatedProduct.getType());
//        if(repository != null) {
//            if(!repository.existsById(productId)) {
//                throw new ProductNotFoundException("Product not found with id: " + productId);
//            }
//            updatedProduct.setId(productId);
//            return repository.save(updatedProduct);
//        }
//        throw new IllegalArgumentException("Invalid Product Type: " + updatedProduct.getClass());
//    }

    public <T extends Product> void deleteProduct(Long productId) {
        ProductType productType = productTypeRepository.findProductTypeById(productId);
        if (productType == null) {
            throw new ProductNotFoundException("Product not found with id: " + productId);
        }
        ProductRepository<? extends Product> repository = repositoryMap.get(productType);
        if (repository == null) {
            throw new IllegalArgumentException("Unknown product type: " + productType);
        }

        repository.deleteById(productId);
    }
}
