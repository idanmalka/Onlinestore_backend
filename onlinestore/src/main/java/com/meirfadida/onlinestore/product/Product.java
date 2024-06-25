package com.meirfadida.onlinestore.product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.meirfadida.onlinestore.cart.Cart;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Book.class, name = "BOOK"),
        @JsonSubTypes.Type(value = Shirt.class, name = "SHIRT")
})
@Table(name = "products")
public abstract class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is mandatory")
    private String name;

    @NotBlank(message = "Product description is mandatory")
    private String description;

    @DecimalMin(value = "0.01", message = "Price must be positive")
    private Double price;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    @JsonIgnoreProperties("items")
    @ManyToMany(mappedBy = "items")
    private List<Cart> carts = new ArrayList<>();

//    @ManyToMany(mappedBy = "items")
//    private List<Cart> carts = new ArrayList<>();

    public Product(String name, String description, Double price, ProductType type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
    }

    public ProductType getType() {
        return type;
    }

    public void addToCart(Cart cart) {
        carts.add(cart);
    }

    public void removeFromCart(Cart cart) {
        carts.remove(cart);
    }


}
