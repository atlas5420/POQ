package com.poq.domain.question.repository;

import com.poq.domain.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {

    List<Question> findByActiveTrue();

    @Query("SELECT q FROM Question q WHERE q.active = true AND (q.speciesType = :speciesType OR q.speciesType IS NULL)")
    List<Question> findActiveQuestionsBySpecies(@Param("speciesType") String speciesType);
}
