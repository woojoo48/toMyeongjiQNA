package com.example.QNA.Question;

import com.example.QNA.anwer.AnswerRepository;
import com.example.QNA.user.User;
import com.example.QNA.user.UserRepository;
import mapper.QNAMapper;
import org.springframework.stereotype.Service;
import com.example.QNA.global.CustomException;
import org.springframework.transaction.annotation.Transactional;

import static com.example.QNA.global.ErrorMSG.*;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final QNAMapper qnaMapper;

    public QuestionService(QuestionRepository questionRepository, UserRepository userRepository, AnswerRepository answerRepository, QNAMapper qnaMapper) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
        this.qnaMapper = qnaMapper;
    }

    @Transactional
    public Long createQuestion(QuestionRequestDTO questionDTO) {
        User user = userRepository.findById(questionDTO.getId())
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
        //스트림을 사용한 이유는 컬렉션을 처리하는데 더 적합하기 때문
        //옵셔널은 단일 값에 대한 null 처리 적합 -> 그러나 스트림은 컬렉션의 여러 요소를 처리하는데 적합
        //이 메서드의 경우 모든 질문 컬렉션이므로 여러 요소를 처리하기 위해 사용
        return questionRepository.findAll().stream()
                .map(qnaMapper::toQuestionDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public QuestionResponseDTO updateQuestion(Long id, QuestionRequestDTO questionDTO) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new CustomException(400, NOT_FOUND_QUESTION + " ID: " + id));

        if (!question.getUser().getId().equals(questionDTO.getId())) {
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
    public void deleteQuestion(Long id, Long userId) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new CustomException(400, NOT_FOUND_QUESTION + " ID: " + id));

        if (!question.getUser().getId().equals(userId)) {
            throw new CustomException(403, UNAUTHORIZED_QUESTION_DELETE);
        }

        if (question.getAnswer() != null) {
            answerRepository.delete(question.getAnswer());
        }

        questionRepository.delete(question);
    }
}
