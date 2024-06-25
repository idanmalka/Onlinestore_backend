package com.meirfadida.onlinestore.user;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.meirfadida.onlinestore.cart.Cart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.cart = new Cart(this);
    }

    public void setCart(Cart cart) {
        if (cart == null) {
            if (this.cart != null) {
                this.cart.setUser(null);
            }
        } else {
            cart.setUser(this);
        }
        this.cart = cart;
    }
}
