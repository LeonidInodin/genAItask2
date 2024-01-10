package com.inodinln.generativeAiTask2.controllers;

import com.inodinln.generativeAiTask2.entities.AuthorEntity;
import com.inodinln.generativeAiTask2.entities.BookEntity;
import com.inodinln.generativeAiTask2.repositories.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/authors/")
@Transactional(readOnly = true)
public class AuthorController {

    private final AuthorRepository authorRepository;

    @GetMapping
    public ResponseEntity<List<AuthorEntity>> getAllAuthors() {
        return new ResponseEntity<>(authorRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorEntity> getAuthorById(@PathVariable(name = "id") Long id) {
        return authorRepository.findById(id).map(authorEntity -> new ResponseEntity<>(authorEntity, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<AuthorEntity> createNewAuthor(@RequestParam(name = "name") String name) {
        return new ResponseEntity<>(authorRepository.save(new AuthorEntity(name)), HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping
    public ResponseEntity<BookEntity> updateAuthor(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "newName", required = false) String newName) {
        Optional<AuthorEntity> result = authorRepository.findById(id);
        if (result.isEmpty()) return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        AuthorEntity entityToBeUpdated = result.get();
        Optional.ofNullable(newName).ifPresent(entityToBeUpdated::setName);
        authorRepository.save(entityToBeUpdated);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable(name = "id") Long id) {
        Optional<AuthorEntity> result = authorRepository.findById(id);
        if (result.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        authorRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
