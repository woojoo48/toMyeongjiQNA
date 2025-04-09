package com.example.QNA.Question;

import com.example.QNA.user.User;
import com.example.QNA.user.UserRepository;

import com.example.QNA.global.CustomException;
import static com.example.QNA.global.ErrorMSG.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service

public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnswerRepository answerRepository;

    //라이프 사이클과 생성시기
    //JWT 로그인 회원가입 공부해보기(여유 있을시)
    @Transactional
    public Long createQuestion(QuestionRequestDTO questionDTO) {

       User user = userRepository.findById(questionDTO.getId())
               .orElseThrow(() -> new CustomException(400, NOT_FOUND_USER));

       String title = questionDTO.getTitle();
       String contents = questionDTO.getContent();
       title = title.trim();
       contents = contents.trim();

        if (title.equals("") || title == null) {
            throw new CustomException(400, NOT_TITLE);
        }

        if (contents.equals("") || contents == null) {
            throw new CustomException(400, NOT_CONTENTS);
        }


        Question question = new Question();
        question.setTitle(questionDTO.getTitle());
        question.setContents(questionDTO.getContent());
        question.setUser(user);

        //String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //User user = userRepository.findByUserName(username).orElseThrow();
        //user.addQuestion(question);
        // ->로그인 기능 미구현 으로 인해 질문 생성이 안되어, 인증 처리 기능 무효화 해둠. 로그인 기능 구현 혹은 리팩토링 필요할듯
        // ->현재 이 코드들 주석 처리 해놓은 이유는 단지 질문이 등록 가능한지 테스트 하기위해

        Question savedQuestion = questionRepository.save(question);
        return savedQuestion.getId();
    }

    @Transactional(readOnly = true)
    //Transactional 찾아보고 공부
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
        //질문 타입이 두개면 리스폰스 타입도 두개 여야함 -> 서로 다른 타입에 각기 해당하는 응답을 리스트에 담아야 하는데 리스트를 두개 만들수도 없고 어떻게 할 것인가.
        //Stream 공부하기, String 형태로 조회

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
