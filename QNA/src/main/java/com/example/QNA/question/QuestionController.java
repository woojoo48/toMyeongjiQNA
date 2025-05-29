package com.example.QNA.question;

import com.example.QNA.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/create")
    public ApiResponse<QuestionResponseDTO> createQuestion(@RequestBody QuestionRequestDTO dto, Authentication authentication) {
        String userId = authentication.getName();
        Long newQuestionId = questionService.createQuestion(dto, userId);

        QuestionResponseDTO responseDTO = questionService.readOneQuestion(newQuestionId);
        return new ApiResponse<>(200, "질문이 정상적으로 생성되었습니다.", responseDTO);
    }

    @GetMapping("/{id}")
    public ApiResponse<QuestionResponseDTO> getQuestion(@PathVariable("id") Long id) {
        QuestionResponseDTO question = questionService.readOneQuestion(id);
        return new ApiResponse<>(200, "질문을 정상적으로 조회하였습니다.", question);
    }

    @GetMapping("/list")
    public ApiResponse<List<QuestionResponseDTO>> getAllQuestions() {
        List<QuestionResponseDTO> questions = questionService.readAllQuestion();
        return new ApiResponse<>(200, "질문목록을 정상적으로 조회하였습니다", questions);
    }

    @PutMapping("/{id}")
    public ApiResponse<QuestionResponseDTO> updateQuestion(@PathVariable("id") Long id, @RequestBody QuestionRequestDTO dto,Authentication authentication) {
        String userId = authentication.getName();
        QuestionResponseDTO updatedQuestion = questionService.updateQuestion(id, dto, userId);
        return new ApiResponse<>(200, "질문이 정상적으로 수정되었습니다.", updatedQuestion);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteQuestion(@PathVariable("id") Long id, Authentication authentication) {
        String userId = authentication.getName();
        questionService.deleteQuestion(id, userId);
        return new ApiResponse<>(200, "질문이 정상적으로 삭제되었습니다.", null);
    }
}