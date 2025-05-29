package com.example.QNA.question;

import com.example.QNA.anwer.AnswerRepository;
import com.example.QNA.user.User;
import com.example.QNA.user.UserRepository;
import com.example.QNA.mapper.QNAMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.QNA.global.CustomException;
import org.springframework.transaction.annotation.Transactional;

import static com.example.QNA.global.ErrorMSG.*;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final QNAMapper qnaMapper;

    @Transactional
    public Long createQuestion(QuestionRequestDTO questionDTO, String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(400, NOT_FOUND_USER));

        String title = questionDTO.getTitle().trim();
        String contents = questionDTO.getContent().trim();

        if (title.isEmpty()) {
            throw new CustomException(400, NOT_TITLE);
        }

        if (contents.isEmpty()) {
            throw new CustomException(400, NOT_CONTENTS);
        }

        Question question = new Question();
        question.setTitle(title);
        question.setContents(contents);
        question.setUser(user);

        return questionRepository.save(question).getId();
    }

    @Transactional(readOnly = true)
    public QuestionResponseDTO readOneQuestion(Long id) {
        Question question = questionRepository.findById(id).orElseThrow();
        return qnaMapper.toQuestionDto(question);
    }

    @Transactional(readOnly = true)
    public List<QuestionResponseDTO> readAllQuestion() {
        return questionRepository.findAll().stream()
                .map(qnaMapper::toQuestionDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public QuestionResponseDTO updateQuestion(Long id, QuestionRequestDTO questionDTO, String userId) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new CustomException(400, NOT_FOUND_QUESTION + " ID: " + id));

        // JWT 토큰에서 받은 userId로 권한 확인
        User currentUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(400, NOT_FOUND_USER));

        if (!question.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new CustomException(403, UNAUTHORIZED_QUESTION_UPDATE);
        }

        String title = questionDTO.getTitle().trim();
        String contents = questionDTO.getContent().trim();

        if (title.isEmpty()) {
            throw new CustomException(400, NOT_TITLE);
        }

        if (contents.isEmpty()) {
            throw new CustomException(400, NOT_CONTENTS);
        }

        question.setTitle(title);
        question.setContents(contents);

        Question updatedQuestion = questionRepository.save(question);
        return qnaMapper.toQuestionDto(updatedQuestion);
    }

    @Transactional
    public void deleteQuestion(Long id, String userId) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new CustomException(400, NOT_FOUND_QUESTION + " ID: " + id));

        User currentUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(400, NOT_FOUND_USER));

        if (!question.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new CustomException(403, UNAUTHORIZED_QUESTION_DELETE);
        }

        if (question.getAnswer() != null) {
            answerRepository.delete(question.getAnswer());
        }

        questionRepository.delete(question);
    }
}
