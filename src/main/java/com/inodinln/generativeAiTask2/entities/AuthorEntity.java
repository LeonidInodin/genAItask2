package com.inodinln.generativeAiTask2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "author")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "author", orphanRemoval = true, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private List<BookEntity> books;

    public AuthorEntity(String name) {
        this.name = name;
    }
}
