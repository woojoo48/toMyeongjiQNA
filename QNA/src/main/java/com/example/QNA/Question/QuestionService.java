package com.example.QNA.Question;

import com.example.QNA.user.User;
import com.example.QNA.user.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
        Question question = new Question();
        question.setTitle(questionDTO.getTitle());
        question.setContents(questionDTO.getContent());

//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = userRepository.findByUserName(username).orElseThrow();
//        user.addQuestion(question);
        // ->로그인 기능 미구현 으로 인해 질문 생성이 안되어, 인증 처리 기능 무효화 해둠. 로그인 기능 구현 혹은 리팩토링 필요할듯
        // ->현재 이 코드들 주석 처리 해놓은 이유는 단지 질문이 등록 가능한지 테스트 하기위해


        Question savedQuestion = questionRepository.save(question);
        return savedQuestion.getId();
    }

    @Transactional(readOnly = true)
    public QuestionResponseDTO readOneQuestion(Long id) {
        Question question = questionRepository.findById(id).orElseThrow();

        QuestionResponseDTO dto = new QuestionResponseDTO();
        dto.setId(question.getId());
        dto.setTitle(question.getTitle());
        dto.setContents(question.getContents());

        if (question.getUser() != null) {
            dto.setUserId(question.getUser().getId());
            dto.setUserName(question.getUser().getUserName());
        }

        dto.setHasAnswer(question.getAnswer() != null);

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
            questionDTO.setContents(question.getContents());

            if (question.getUser() != null) {
                questionDTO.setUserId(question.getUser().getId());
                questionDTO.setUserName(question.getUser().getUserName());
            }
            questionDTO.setHasAnswer(question.getAnswer() != null);

            dtos.add(questionDTO);
        }
        return dtos;
    }

}
