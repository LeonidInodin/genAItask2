package com.inodinln.generativeAiTask2;

import com.inodinln.generativeAiTask2.controllers.AuthorController;
import com.inodinln.generativeAiTask2.entities.AuthorEntity;
import com.inodinln.generativeAiTask2.entities.BookEntity;
import com.inodinln.generativeAiTask2.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthorControllerUnitTests {

    @InjectMocks
    private AuthorController authorController;

    @Mock
    private AuthorRepository authorRepository;

    @Test
    void getAllAuthors() {
        // Arrange
        List<AuthorEntity> authors = Arrays.asList(new AuthorEntity(), new AuthorEntity());
        when(authorRepository.findAll()).thenReturn(authors);

        // Act
        ResponseEntity<List<AuthorEntity>> response = authorController.getAllAuthors();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authors, response.getBody());
    }

    @Test
    void getAllAuthorsEmptyList() {
        // Arrange
        when(authorRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<List<AuthorEntity>> response = authorController.getAllAuthors();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getAuthorById() {
        // Arrange
        Long authorId = 1L;
        AuthorEntity authorEntity = new AuthorEntity();
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(authorEntity));

        // Act
        ResponseEntity<AuthorEntity> response = authorController.getAuthorById(authorId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authorEntity, response.getBody());
    }

    @Test
    void getAuthorByIdNotFound() {
        // Arrange
        Long authorId = 1L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<AuthorEntity> response = authorController.getAuthorById(authorId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createNewAuthor() {
        // Arrange
        String authorName = "New Author";
        when(authorRepository.save(any())).thenReturn(new AuthorEntity(authorName));

        // Act
        ResponseEntity<AuthorEntity> response = authorController.createNewAuthor(authorName);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(authorRepository, times(1)).save(any());
    }

    @Test
    void updateAuthor() {
        // Arrange
        Long authorId = 1L;
        String newName = "Updated Author";

        AuthorEntity existingAuthorEntity = new AuthorEntity();
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthorEntity));

        // Act
        ResponseEntity<BookEntity> response = authorController.updateAuthor(authorId, newName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authorRepository, times(1)).save(existingAuthorEntity);
        assertEquals(newName, existingAuthorEntity.getName());
    }

    @Test
    void updateAuthorNotFound() {
        // Arrange
        Long authorId = 1L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<BookEntity> response = authorController.updateAuthor(authorId, "Updated Author");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(authorRepository, never()).save(any());
    }

    @Test
    void deleteAuthorById() {
        // Arrange
        Long authorId = 1L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(new AuthorEntity()));

        // Act
        ResponseEntity<Void> response = authorController.deleteAuthorById(authorId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(authorRepository, times(1)).deleteById(authorId);
    }

    @Test
    void deleteAuthorByIdNotFound() {
        // Arrange
        Long authorId = 1L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = authorController.deleteAuthorById(authorId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(authorRepository, never()).deleteById(any());
    }

}
