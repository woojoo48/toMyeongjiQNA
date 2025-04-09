package com.example.QNA.Question;

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

    //질문 번호로 주소 찍어야 함
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
}