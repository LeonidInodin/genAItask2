package com.inodinln.generativeAiTask2.repositories;

import com.inodinln.generativeAiTask2.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findAllByAuthor_Id(Long id);

    List<BookEntity> findAllByGenre_Id(Long id);

    Optional<BookEntity> findByTitle(String title);
}
