package com.inodinln.generativeAiTask2.controllers;

import com.inodinln.generativeAiTask2.entities.BookEntity;
import com.inodinln.generativeAiTask2.entities.GenreEntity;
import com.inodinln.generativeAiTask2.repositories.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/genres/")
@Transactional(readOnly = true)
public class GenreController {

    private final GenreRepository genreRepository;

    @GetMapping
    public ResponseEntity<List<GenreEntity>> getAllGenres() {
        return new ResponseEntity<>(genreRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreEntity> getGenreById(@PathVariable(name = "id") Long id) {
        return genreRepository.findById(id).map(genreEntity -> new ResponseEntity<>(genreEntity, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<GenreEntity> createNewGenre(@RequestParam(name = "title") String title) {
        return new ResponseEntity<>(genreRepository.save(new GenreEntity(title)), HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping
    public ResponseEntity<BookEntity> updateGenre(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "newTitle", required = false) String newTitle) {
        Optional<GenreEntity> result = genreRepository.findById(id);
        if (result.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        GenreEntity entityToBeUpdated = result.get();
        Optional.ofNullable(newTitle).ifPresent(entityToBeUpdated::setTitle);
        genreRepository.save(entityToBeUpdated);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenreById(@PathVariable(name = "id") Long id) {
        Optional<GenreEntity> result = genreRepository.findById(id);
        if (result.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        genreRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
