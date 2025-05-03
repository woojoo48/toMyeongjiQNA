package com.example.QNA.Question;

import com.example.QNA.user.User;
import com.example.QNA.user.UserRepository;

import com.example.QNA.global.CustomException;
import static com.example.QNA.global.ErrorMSG.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;

    public QuestionService(QuestionRepository questionRepository, UserRepository userRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
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

    private QuestionResponseDTO convertToDTO(Question question) {
        QuestionResponseDTO dto = new QuestionResponseDTO();
        dto.setId(question.getId());
        dto.setTitle(question.getTitle());
        dto.setContents(question.getContents());

        Optional.ofNullable(question.getUser()).ifPresent(user -> {
            dto.setUserId(user.getId());
            dto.setUserName(user.getUserName());
        });

        dto.setHasAnswer(question.getAnswer() != null);

        return dto;
    }

    @Transactional(readOnly = true)
    public QuestionResponseDTO readOneQuestion(Long id) {
        Question question = questionRepository.findById(id).orElseThrow();
        return convertToDTO(question);
    }

    @Transactional(readOnly = true)
    public List<QuestionResponseDTO> readAllQuestion() {
        return questionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
