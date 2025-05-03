package com.example.QNA.Question;

import com.example.QNA.global.CustomException;
import static com.example.QNA.global.ErrorMSG.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AnswerService {

    private AnswerRepository answerRepository;
    private QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public Long createAnswer(AnswerRequestDTO answerRequestDTO, Long questionId) {
        if (answerRepository.existsByQuestionId(questionId)) {
            throw new CustomException(400, ALREADY_HAS_ANSWER);
        }

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(400, NOT_FOUND_QUESTION + " ID: " + questionId));

        Answer answer = new Answer();
        answer.setQuestionTitle(answerRequestDTO.getQuestionTitle());
        answer.setContents(answerRequestDTO.getContents());
        answer.setQuestion(question);

        return answerRepository.save(answer).getId();
    }

    @Transactional(readOnly = true)
    public AnswerResponseDTO readAnswer(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(400, NOT_FOUND_QUESTION + " ID: " + questionId));

        return Optional.ofNullable(question.getAnswer())
                .map(answer -> {
                    AnswerResponseDTO dto = new AnswerResponseDTO();
                    dto.setId(answer.getId());
                    dto.setQuestionTitle(answer.getQuestionTitle());
                    dto.setContents(answer.getContents());
                    dto.setQuestionId(questionId);
                    return dto;
                })
                .orElseThrow(() -> new CustomException(400, NOT_FOUND_ANSWER));
    }
}