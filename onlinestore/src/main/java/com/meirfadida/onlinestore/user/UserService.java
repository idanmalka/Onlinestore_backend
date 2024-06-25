package com.meirfadida.onlinestore.user;

import com.meirfadida.onlinestore.cart.Cart;
import com.meirfadida.onlinestore.cart.CartService;
import com.meirfadida.onlinestore.exception.EmailAlreadyExitsException;
import com.meirfadida.onlinestore.exception.UsernameAlreadyExistsException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CartService cartService;

    @Autowired
    public UserService(UserRepository userRepository, CartService cartService) {
        this.userRepository = userRepository;
        this.cartService = cartService;
    }

    public User createUser(User user) {
        if(userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException("Username " + user.getUsername() + " Already Exists.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExitsException("Email already exists");
        }

        Cart cart = new Cart();
        user.setCart(cart);
        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found: " + userId));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        // Important - Password we'll be updated elsewhere.

        return userRepository.save(existingUser);
    }

    @Transactional
    public void addToCart(Long userId, Long productId) {
        User user = getUserById(userId);
        Long cartId = user.getCart().getId();
        cartService.addProductToCart(cartId, productId);
        userRepository.save(user);
    }

    @Transactional
    public void removeFromCart(Long userId, Long productId) {
        User user = getUserById(userId);
        Long cartId = user.getCart().getId();
        cartService.removeProductToCart(cartId, productId);
        userRepository.save(user);
    }

    @Transactional
    public void clearCart(Long userId) {
        User user = getUserById(userId);
        Long cartId = user.getCart().getId();
        cartService.clearCart(cartId);
        userRepository.save(user);
    }


}
