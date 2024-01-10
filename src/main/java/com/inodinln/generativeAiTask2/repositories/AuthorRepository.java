package com.inodinln.generativeAiTask2.repositories;

import com.inodinln.generativeAiTask2.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

}
