package com.meirfadida.onlinestore.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shirt extends Product {
    private String size;
    private String color;

    public Shirt(@JsonProperty("name") String name,
                 @JsonProperty("description") String description,
                 @JsonProperty("price") Double price,
                 @JsonProperty("size") String size,
                 @JsonProperty("color") String color) {
        super(name, description, price, ProductType.Shirt);
        this.size = size;
        this.color = color;
    }
}

