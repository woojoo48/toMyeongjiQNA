package com.example.QNA.Question;

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
    public void createAnswer(AnswerRequestDTO answerRequestDTO,Long questionId ) {
        //기존 답변 확인 메서드
        if (answerRepository.existsByQuestionId(questionId)) {
            throw new IllegalStateException("해당 질문에는 이미 답변이 존재합니다.");
        }

        Question question = questionRepository.findById(questionId).orElseThrow();

        Answer answer = new Answer();
        answer.setQuestionTitle(answerRequestDTO.getQuestionTitle());
        answer.setContents(answerRequestDTO.getContents());
        answer.setQuestion(question);

        answerRepository.save(answer);
    }

    @Transactional(readOnly = true)
    public AnswerResponseDTO readAnswer(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow();
        Answer answer = question.getAnswer();

        AnswerResponseDTO dto = new AnswerResponseDTO();
        dto.setId(answer.getId());
        dto.setQuestionTitle(answer.getQuestionTitle());
        dto.setContents(answer.getContents());

        return dto;
    }

}