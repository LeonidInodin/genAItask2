package com.inodinln.generativeAiTask2;

import com.inodinln.generativeAiTask2.controllers.GenreController;
import com.inodinln.generativeAiTask2.entities.BookEntity;
import com.inodinln.generativeAiTask2.entities.GenreEntity;
import com.inodinln.generativeAiTask2.repositories.GenreRepository;
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
class GenreControllerUnitTests {

    @InjectMocks
    private GenreController genreController;

    @Mock
    private GenreRepository genreRepository;

    @Test
    void getAllGenres() {
        // Arrange
        List<GenreEntity> genres = Arrays.asList(new GenreEntity(), new GenreEntity());
        when(genreRepository.findAll()).thenReturn(genres);

        // Act
        ResponseEntity<List<GenreEntity>> response = genreController.getAllGenres();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(genres, response.getBody());
    }

    @Test
    void getAllGenresEmptyList() {
        // Arrange
        when(genreRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<List<GenreEntity>> response = genreController.getAllGenres();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getGenreById() {
        // Arrange
        Long genreId = 1L;
        GenreEntity genreEntity = new GenreEntity();
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genreEntity));

        // Act
        ResponseEntity<GenreEntity> response = genreController.getGenreById(genreId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(genreEntity, response.getBody());
    }

    @Test
    void getGenreByIdNotFound() {
        // Arrange
        Long genreId = 1L;
        when(genreRepository.findById(genreId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<GenreEntity> response = genreController.getGenreById(genreId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createNewGenre() {
        // Arrange
        String genreTitle = "New Genre";
        when(genreRepository.save(any())).thenReturn(new GenreEntity(genreTitle));

        // Act
        ResponseEntity<GenreEntity> response = genreController.createNewGenre(genreTitle);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(genreRepository, times(1)).save(any());
    }

    @Test
    void updateGenre() {
        // Arrange
        Long genreId = 1L;
        String newTitle = "Updated Genre";

        GenreEntity existingGenreEntity = new GenreEntity();
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(existingGenreEntity));

        // Act
        ResponseEntity<BookEntity> response = genreController.updateGenre(genreId, newTitle);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(genreRepository, times(1)).save(existingGenreEntity);
        assertEquals(newTitle, existingGenreEntity.getTitle());
    }

    @Test
    void updateGenreNotFound() {
        // Arrange
        Long genreId = 1L;
        when(genreRepository.findById(genreId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<BookEntity> response = genreController.updateGenre(genreId, "Updated Genre");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(genreRepository, never()).save(any());
    }

    @Test
    void deleteGenreById() {
        // Arrange
        Long genreId = 1L;
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(new GenreEntity()));

        // Act
        ResponseEntity<Void> response = genreController.deleteGenreById(genreId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(genreRepository, times(1)).deleteById(genreId);
    }

    @Test
    void deleteGenreByIdNotFound() {
        // Arrange
        Long genreId = 1L;
        when(genreRepository.findById(genreId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = genreController.deleteGenreById(genreId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(genreRepository, never()).deleteById(any());
    }

}
