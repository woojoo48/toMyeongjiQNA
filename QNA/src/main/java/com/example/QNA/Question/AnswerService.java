package com.example.QNA.Question;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public Long createAnswer(AnswerRequestDTO answerRequestDTO, Long questionId) {

        if (answerRepository.existsByQuestionId(questionId)) {
            throw new IllegalStateException("해당 질문에는 이미 답변이 존재합니다.");
        }

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다. ID: " + questionId));

        Answer answer = new Answer();
        answer.setQuestionTitle(answerRequestDTO.getQuestionTitle());
        answer.setContents(answerRequestDTO.getContents());
        answer.setQuestion(question);

        Answer savedAnswer = answerRepository.save(answer);
        return savedAnswer.getId();
    }

    @Transactional(readOnly = true)
    public AnswerResponseDTO readAnswer(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다. ID: " + questionId));

        Answer answer = question.getAnswer();
        if (answer == null) {
            throw new IllegalStateException("해당 질문에 대한 답변이 없습니다.");
        }

        AnswerResponseDTO dto = new AnswerResponseDTO();
        dto.setId(answer.getId());
        dto.setQuestionTitle(answer.getQuestionTitle());
        dto.setContents(answer.getContents());
        dto.setQuestionId(questionId);

        return dto;
    }

}