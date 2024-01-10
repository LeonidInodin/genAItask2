package com.inodinln.generativeAiTask2;

import com.inodinln.generativeAiTask2.controllers.BookController;
import com.inodinln.generativeAiTask2.entities.AuthorEntity;
import com.inodinln.generativeAiTask2.entities.BookEntity;
import com.inodinln.generativeAiTask2.entities.GenreEntity;
import com.inodinln.generativeAiTask2.repositories.AuthorRepository;
import com.inodinln.generativeAiTask2.repositories.BookRepository;
import com.inodinln.generativeAiTask2.repositories.GenreRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookControllerUnitTests {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private GenreRepository genreRepository;

    @Test
    void getAllAvailableBooks() {
        // Arrange
        List<BookEntity> books = Arrays.asList(new BookEntity(), new BookEntity());
        when(bookRepository.findAll()).thenReturn(books);

        // Act
        ResponseEntity<List<BookEntity>> response = bookController.getAllAvailableBooks();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books.size(), response.getBody().size());
    }

    @Test
    void getAllAvailableBooksEmptyList() {
        // Arrange
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<BookEntity>> response = bookController.getAllAvailableBooks();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void findBooksByAuthorId() {
        // Arrange
        Long authorId = 1L;
        List<BookEntity> books = Arrays.asList(new BookEntity(), new BookEntity());
        when(bookRepository.findAllByAuthor_Id(authorId)).thenReturn(books);

        // Act
        ResponseEntity<List<BookEntity>> response = bookController.findBooksByAuthorId(authorId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
    }

    @Test
    void findBooksByGenreId() {
        // Arrange
        Long genreId = 1L;
        List<BookEntity> books = Arrays.asList(new BookEntity(), new BookEntity());
        when(bookRepository.findAllByGenre_Id(genreId)).thenReturn(books);

        // Act
        ResponseEntity<List<BookEntity>> response = bookController.findBooksByGenreId(genreId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
    }

    @Test
    void getBookByTitle() {
        // Arrange
        String bookTitle = "title";
        BookEntity bookEntity = new BookEntity();
        when(bookRepository.findByTitle(bookTitle)).thenReturn(Optional.of(bookEntity));

        // Act
        ResponseEntity<BookEntity> response = bookController.findBookByTitle(bookTitle);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getBookById() {
        // Arrange
        Long bookId = 1L;
        BookEntity bookEntity = new BookEntity();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookEntity));

        // Act
        ResponseEntity<BookEntity> response = bookController.getBookById(bookId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getBookByIdNotFound() {
        // Arrange
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<BookEntity> response = bookController.getBookById(bookId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createNewBook() {
        // Arrange
        String title = "New Book";
        Long authorId = 1L;
        Long genreId = 2L;
        BigDecimal price = BigDecimal.valueOf(19.99);
        Integer quantity = 50;

        AuthorEntity authorEntity = new AuthorEntity();
        GenreEntity genreEntity = new GenreEntity();

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(authorEntity));
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genreEntity));
        when(bookRepository.save(any())).thenReturn(new BookEntity());

        // Act
        ResponseEntity<BookEntity> response = bookController.createNewBook(title, authorId, genreId, price, quantity);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void updateBook() {
        // Arrange
        Long bookId = 1L;
        String newTitle = "Updated Title";
        BigDecimal newPrice = BigDecimal.valueOf(25.99);
        Integer newQuantity = 50;

        BookEntity existingBookEntity = new BookEntity();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBookEntity));

        // Act
        ResponseEntity<BookEntity> response = bookController.updateBook(bookId, newTitle, newPrice, newQuantity);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(bookRepository, times(1)).save(existingBookEntity);
        assertEquals(newTitle, existingBookEntity.getTitle());
        assertEquals(newPrice, existingBookEntity.getPrice());
        assertEquals(newQuantity, existingBookEntity.getQuantity());
    }

    @Test
    void updateBookNotFound() {
        // Arrange
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<BookEntity> response = bookController.updateBook(bookId, "Updated Title", BigDecimal.valueOf(25.99), 50);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(bookRepository, never()).save(any());
    }

    @Test
    void deleteBookById() {
        // Arrange
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(new BookEntity()));

        // Act
        ResponseEntity<Void> response = bookController.deleteBookById(bookId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    void deleteBookByIdNotFound() {
        // Arrange
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = bookController.deleteBookById(bookId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(bookRepository, never()).deleteById(any());
    }

}
