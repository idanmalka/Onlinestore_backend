package com.meirfadida.onlinestore.cart;

import com.meirfadida.onlinestore.exception.CartNotFoundException;
import com.meirfadida.onlinestore.product.Product;
import com.meirfadida.onlinestore.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;

    @Autowired
    public CartService(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    public Cart getCartById(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart Not Found with Id: " + cartId));

        return cart;
    }

    public List<Product> getCartItems(Long cartId) {
        Cart cart = getCartById(cartId);
        return new ArrayList<>(cart.getItems());
    }

    public void addProductToCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart Not Found with Id: " + cartId));
        Product product = productService.getProductById(productId);
        cart.addProduct(product);
    }

    public void removeProductToCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart Not Found with Id: " + cartId));
        Product product = productService.getProductById(productId);
        cart.removeProduct(product);
    }

    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart Not Found with Id: " + cartId));
        cart.clearItems();
    }

}
