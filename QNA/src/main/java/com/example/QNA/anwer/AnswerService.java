package com.example.QNA.anwer;

import com.example.QNA.question.Question;
import com.example.QNA.question.QuestionRepository;
import com.example.QNA.global.CustomException;
import static com.example.QNA.global.ErrorMSG.*;

import com.example.QNA.user.User;
import com.example.QNA.user.UserRepository;
import com.example.QNA.mapper.QNAMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final QNAMapper qnaMapper;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository,UserRepository userRepository,QNAMapper qnaMapper) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.qnaMapper = qnaMapper;
    }

    @Transactional
    public Long createAnswer(AnswerRequestDTO answerRequestDTO, Long questionId) {
        if (answerRepository.existsByQuestionId(questionId)) {
            throw new CustomException(400, ALREADY_HAS_ANSWER);
        }

        String title = answerRequestDTO.getAnswerTitle().trim();
        String contents = answerRequestDTO.getContents().trim();

        if (title.isEmpty()) {
            throw new CustomException(400, NOT_TITLE);
        }

        if (contents.isEmpty()) {
            throw new CustomException(400, NOT_CONTENTS);
        }

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(400, NOT_FOUND_QUESTION + " ID: " + questionId));

        Answer answer = new Answer();
        answer.setAnswerTitle(answerRequestDTO.getAnswerTitle());
        answer.setContents(answerRequestDTO.getContents());
        answer.setQuestion(question);

        return answerRepository.save(answer).getId();
    }

    @Transactional(readOnly = true)
    public AnswerResponseDTO readAnswer(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(400, NOT_FOUND_QUESTION + " ID: " + questionId));

        return Optional.ofNullable(question.getAnswer())
                .map(qnaMapper::toAnswerDto)
                .orElseThrow(() -> new CustomException(400, NOT_FOUND_ANSWER));
    }

    @Transactional
    public AnswerResponseDTO updateAnswer(Long answerId, AnswerRequestDTO answerRequestDTO, Long userId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new CustomException(400, NOT_FOUND_ANSWER));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(400, NOT_FOUND_USER));

        String answerTitle = answerRequestDTO.getAnswerTitle().trim();
        String contents = answerRequestDTO.getContents().trim();
        if(answerTitle.isEmpty()){
            throw new CustomException(400, NOT_TITLE);
        }

        if (contents.isEmpty()) {
            throw new CustomException(400, NOT_CONTENTS);
        }

        if ("Student".equals(user.getRole())) {
            throw new CustomException(403, UNAUTHORIZED_ANSWER_UPDATE);
        } else{
            String questionTitle = answerRequestDTO.getAnswerTitle().trim();
            if (!questionTitle.isEmpty()) {
                answer.setAnswerTitle(questionTitle);
            }

            answer.setContents(contents);

            Answer updatedAnswer = answerRepository.save(answer);
            return qnaMapper.toAnswerDto(updatedAnswer);
        }
    }
}
