package com.example.QNA.mapper;

import com.example.QNA.anwer.Answer;
import com.example.QNA.anwer.AnswerResponseDTO;
import com.example.QNA.college.College;
import com.example.QNA.question.Question;
import com.example.QNA.question.QuestionResponseDTO;
import com.example.QNA.studentclub.StudentClub;
import com.example.QNA.user.User;
import com.example.QNA.user.UserResponseDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-22T11:16:36+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (JetBrains s.r.o.)"
)
@Component
public class QNAMapperImpl implements QNAMapper {

    @Override
    public QuestionResponseDTO toQuestionDto(Question question) {
        if ( question == null ) {
            return null;
        }

        QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();

        questionResponseDTO.setUserId( questionUserId( question ) );
        questionResponseDTO.setUserName( questionUserUserName( question ) );
        questionResponseDTO.setId( question.getId() );
        questionResponseDTO.setTitle( question.getTitle() );
        questionResponseDTO.setContents( question.getContents() );

        questionResponseDTO.setHasAnswer( question.getAnswer() != null );

        return questionResponseDTO;
    }

    @Override
    public AnswerResponseDTO toAnswerDto(Answer answer) {
        if ( answer == null ) {
            return null;
        }

        AnswerResponseDTO answerResponseDTO = new AnswerResponseDTO();

        answerResponseDTO.setQuestionId( answerQuestionId( answer ) );
        answerResponseDTO.setId( answer.getId() );
        answerResponseDTO.setAnswerTitle( answer.getAnswerTitle() );
        answerResponseDTO.setContents( answer.getContents() );

        return answerResponseDTO;
    }

    @Override
    public UserResponseDTO toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setStudentClubName( userStudentClubStudentClubName( user ) );
        userResponseDTO.setCollegeName( userStudentClubCollegeCollegeName( user ) );
        userResponseDTO.setUserId( user.getUserId() );
        userResponseDTO.setUserName( user.getUserName() );
        userResponseDTO.setRole( user.getRole() );

        return userResponseDTO;
    }

    private Long questionUserId(Question question) {
        if ( question == null ) {
            return null;
        }
        User user = question.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String questionUserUserName(Question question) {
        if ( question == null ) {
            return null;
        }
        User user = question.getUser();
        if ( user == null ) {
            return null;
        }
        String userName = user.getUserName();
        if ( userName == null ) {
            return null;
        }
        return userName;
    }

    private Long answerQuestionId(Answer answer) {
        if ( answer == null ) {
            return null;
        }
        Question question = answer.getQuestion();
        if ( question == null ) {
            return null;
        }
        Long id = question.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String userStudentClubStudentClubName(User user) {
        if ( user == null ) {
            return null;
        }
        StudentClub studentClub = user.getStudentClub();
        if ( studentClub == null ) {
            return null;
        }
        String studentClubName = studentClub.getStudentClubName();
        if ( studentClubName == null ) {
            return null;
        }
        return studentClubName;
    }

    private String userStudentClubCollegeCollegeName(User user) {
        if ( user == null ) {
            return null;
        }
        StudentClub studentClub = user.getStudentClub();
        if ( studentClub == null ) {
            return null;
        }
        College college = studentClub.getCollege();
        if ( college == null ) {
            return null;
        }
        String collegeName = college.getCollegeName();
        if ( collegeName == null ) {
            return null;
        }
        return collegeName;
    }
}
