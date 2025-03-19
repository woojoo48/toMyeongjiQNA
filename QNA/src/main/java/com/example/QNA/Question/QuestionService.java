package com.example.QNA.Question;

import com.example.QNA.user.User;
import com.example.QNA.user.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public QuestionService(QuestionRepository questionRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createQuestion(QuestionRequestDTO questionDTO) {
        Question question = new Question();
        question.setTitle(questionDTO.getTitle());
        question.setContents(questionDTO.getContent());

        questionRepository.save(question);

        //연결
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        user.addQuestion(question);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public QuestionResponseDTO readOneQuestion(Long id) {
        Question question = questionRepository.findById(id).orElseThrow();

        QuestionResponseDTO dto = new QuestionResponseDTO();
        dto.setId(question.getId());
        dto.setTitle(question.getTitle());
        dto.setContent(question.getContents());

        return dto;
    }

    @Transactional(readOnly = true)
    public List<QuestionResponseDTO> readAllQuestion() {
        List<Question> questionList = questionRepository.findAll();

        List<QuestionResponseDTO> dtos = new ArrayList<>();
        for(Question question : questionList) {
            QuestionResponseDTO questionDTO = new QuestionResponseDTO();
            questionDTO.setId(question.getId());
            questionDTO.setTitle(question.getTitle());
            questionDTO.setContent(question.getContents());
            dtos.add(questionDTO);
        }
        return dtos;
    }

}
