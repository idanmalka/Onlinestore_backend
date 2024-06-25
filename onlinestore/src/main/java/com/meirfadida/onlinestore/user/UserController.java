package com.meirfadida.onlinestore.user;

import com.meirfadida.onlinestore.cart.CartService;
import com.meirfadida.onlinestore.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final CartService cartService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, CartService cartService,
                          UserRepository userRepository) {
        this.userService = userService;
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return createdUser;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user;
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id) {
        User updatedUser = userService.updateUser(id, user);
        return updatedUser;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/{userId}/cart/items/{productId}")
    public void addToCart(@PathVariable Long userId, @PathVariable Long productId) {
        userService.addToCart(userId, productId);
    }

    @DeleteMapping("/{userId}/cart/items/{productId}")
    public void removeFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        userService.removeFromCart(userId, productId);
    }

    @GetMapping("/{userId}/cart")
    public List<Product> getCartItems(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        List<Product> cartItems = cartService.getCartItems(user.getCart().getId());
        return cartItems;
    }

    @DeleteMapping("/{userId}/cart")
    public void clearCart(@PathVariable Long userId) {
        userService.clearCart(userId);
    }
}
