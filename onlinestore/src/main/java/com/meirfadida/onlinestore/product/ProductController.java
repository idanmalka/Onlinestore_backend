package com.meirfadida.onlinestore.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<? extends Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        return product;
    }

    @GetMapping(path = "/type/{productType}")
    public List<? extends Product> getProductsByType(@PathVariable("productType") ProductType productType) {
        return productService.getAllProductsByType(productType);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

//    @PutMapping(path = "/{productId}")
//    public <T extends Product> T updateProduct(@PathVariable Long productId, @RequestBody T updatedProduct) {
//        return productService.updateProduct(updatedProduct, productId);
//    }

    @DeleteMapping("/{productId}")
    public <T extends Product> void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }





}