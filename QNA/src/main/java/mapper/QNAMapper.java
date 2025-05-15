package mapper;

import com.example.QNA.anwer.Answer;
import com.example.QNA.anwer.AnswerResponseDTO;
import com.example.QNA.Question.Question;
import com.example.QNA.Question.QuestionResponseDTO;
import com.example.QNA.user.User;
import com.example.QNA.user.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QNAMapper {
    QNAMapper INSTANCE = Mappers.getMapper(QNAMapper.class);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.userName")
    @Mapping(target = "hasAnswer", expression = "java(question.getAnswer() != null)")
    QuestionResponseDTO toQuestionDto(Question question);

    @Mapping(target = "questionId", source = "question.id")
    AnswerResponseDTO toAnswerDto(Answer answer);

    @Mapping(target = "studentClubName", source = "studentClub.studentClubName")
    @Mapping(target = "collegeName", source = "studentClub.college.collegeName")
    UserResponseDTO toUserDto(User user);
}
