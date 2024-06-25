package com.meirfadida.onlinestore.cart;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.meirfadida.onlinestore.product.Product;
import com.meirfadida.onlinestore.user.User;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @OneToOne(mappedBy = "cart")
    private User user;

    @JsonIgnoreProperties("carts")
    @ManyToMany
    @JoinTable(
            name = "cart_items",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> items = new ArrayList<>();

    public Cart(User user) {
        this.user = user;
    }

    public void addProduct(Product product) {
        if (items.add(product)) {
            product.addToCart(this);
        }
    }

    // Need to add a cartRepository.save() in the service, for persisting the info
    public void removeProduct(Product product) {
        if (items.remove(product)) {
            product.removeFromCart(this);
        }
    }

    public void clearItems() {
        for (Product product : new ArrayList<>(items)) {
            removeProduct(product);
        }
    }
}

