package com.example.QNA.question;

import com.example.QNA.global.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/create")
    public ApiResponse<QuestionRequestDTO> createQuestion(@RequestBody QuestionRequestDTO dto) {
        Long newQuestionId = questionService.createQuestion(dto);
        return new ApiResponse<>(200, "질문이 정상적으로 생성되었습니다.", dto);
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
    public ApiResponse<QuestionResponseDTO> updateQuestion(
            @PathVariable("id") Long id,
            @RequestBody QuestionRequestDTO dto) {
        QuestionResponseDTO updatedQuestion = questionService.updateQuestion(id, dto);
        return new ApiResponse<>(200, "질문이 정상적으로 수정되었습니다.", updatedQuestion);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteQuestion(
            @PathVariable("id") Long id,
            @RequestParam("userId") Long userId) {
        questionService.deleteQuestion(id, userId);
        return new ApiResponse<>(200, "질문이 정상적으로 삭제되었습니다.", null);
    }
}