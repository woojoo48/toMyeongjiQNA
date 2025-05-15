package com.example.QNA.anwer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository <Answer, Long> {
    boolean existsByQuestionId(Long questionId);
    Optional<Answer> findById(Long questionId);
}
