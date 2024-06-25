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
public class Book extends Product {
    private String author;
    private String genre;

    public Book(@JsonProperty("name") String name,
                @JsonProperty("description") String description,
                @JsonProperty("price") Double price,
                @JsonProperty("author") String author,
                @JsonProperty("genre") String genre) {
        super(name, description, price, ProductType.Book);
        this.author = author;
        this.genre = genre;
    }
}
