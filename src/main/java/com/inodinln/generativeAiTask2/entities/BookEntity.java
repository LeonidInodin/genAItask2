package com.inodinln.generativeAiTask2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 200)
    private String title;

    @ManyToOne
    @JoinColumn(name = "authorId", nullable = false)
    private AuthorEntity author;

    @ManyToOne
    @JoinColumn(name = "genreId", nullable = false)
    private GenreEntity genre;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private int quantity;

    public BookEntity(String title, AuthorEntity author, GenreEntity genre, BigDecimal price, int quantity) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.quantity = quantity;
    }
}
