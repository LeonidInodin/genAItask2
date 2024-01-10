package com.inodinln.generativeAiTask2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "genre")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100)
    private String title;

    @JsonIgnore
    @OneToMany(mappedBy = "genre", orphanRemoval = true, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private List<BookEntity> books;

    public GenreEntity(String title) {
        this.title = title;
    }
}
