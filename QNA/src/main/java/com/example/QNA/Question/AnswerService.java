package com.example.QNA.Question;

import com.example.QNA.global.CustomException;
import static com.example.QNA.global.ErrorMSG.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

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

        Answer savedAnswer = answerRepository.save(answer);
        return savedAnswer.getId();
    }

    @Transactional(readOnly = true)
    public AnswerResponseDTO readAnswer(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(400, NOT_FOUND_QUESTION + " ID: " + questionId));

        Answer answer = question.getAnswer();
        if (answer == null) {
            throw new CustomException(400, NOT_FOUND_ANSWER);
        }

        AnswerResponseDTO dto = new AnswerResponseDTO();
        dto.setId(answer.getId());
        dto.setQuestionTitle(answer.getQuestionTitle());
        dto.setContents(answer.getContents());
        dto.setQuestionId(questionId);

        return dto;
    }
}