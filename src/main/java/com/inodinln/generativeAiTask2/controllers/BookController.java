package com.inodinln.generativeAiTask2.controllers;

import com.inodinln.generativeAiTask2.entities.AuthorEntity;
import com.inodinln.generativeAiTask2.entities.BookEntity;
import com.inodinln.generativeAiTask2.entities.GenreEntity;
import com.inodinln.generativeAiTask2.repositories.AuthorRepository;
import com.inodinln.generativeAiTask2.repositories.BookRepository;
import com.inodinln.generativeAiTask2.repositories.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/books/")
@Transactional(readOnly = true)
public class BookController {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    @GetMapping
    public ResponseEntity<List<BookEntity>> getAllAvailableBooks() {
        return new ResponseEntity<>(bookRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/findByAuthorId/{id}")
    public ResponseEntity<List<BookEntity>> findBooksByAuthorId(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(bookRepository.findAllByAuthor_Id(id), HttpStatus.OK);
    }

    @GetMapping("/findByGenreId/{id}")
    public ResponseEntity<List<BookEntity>> findBooksByGenreId(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(bookRepository.findAllByGenre_Id(id), HttpStatus.OK);
    }

    @GetMapping("/findByTitle")
    public ResponseEntity<BookEntity> findBookByTitle( @RequestParam(name = "title") String title) {
        return bookRepository.findByTitle(title).map(bookEntity -> new ResponseEntity<>(bookEntity, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable(name = "id") Long id) {
        return bookRepository.findById(id).map(bookEntity -> new ResponseEntity<>(bookEntity, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<BookEntity> createNewBook(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "authorId") Long authorId,
            @RequestParam(name = "genreId") Long genreId,
            @RequestParam(name = "price") BigDecimal price,
            @RequestParam(name = "quantity") Integer quantity) {
        Optional<AuthorEntity> author = authorRepository.findById(authorId);
        Optional<GenreEntity> genre = genreRepository.findById(genreId);
        if (genre.isEmpty() || author.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(bookRepository.save(new BookEntity(title,author.get(), genre.get(), price, quantity)), HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping
    public ResponseEntity<BookEntity> updateBook(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "newTitle", required = false) String newTitle,
            @RequestParam(name = "newPrice", required = false) BigDecimal newPrice,
            @RequestParam(name = "quantity", required = false) Integer newQuantity) {
        Optional<BookEntity> result = bookRepository.findById(id);
        if (result.isEmpty()) return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        BookEntity entityToBeUpdated = result.get();
        Optional.ofNullable(newTitle).ifPresent(entityToBeUpdated::setTitle);
        Optional.ofNullable(newPrice).ifPresent(entityToBeUpdated::setPrice);
        Optional.ofNullable(newQuantity).ifPresent(entityToBeUpdated::setQuantity);
        bookRepository.save(entityToBeUpdated);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable(name = "id") Long id) {
        Optional<BookEntity> result = bookRepository.findById(id);
        if (result.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        bookRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
