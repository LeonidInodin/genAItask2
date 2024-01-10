package com.inodinln.generativeAiTask2.repositories;

import com.inodinln.generativeAiTask2.entities.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

}
