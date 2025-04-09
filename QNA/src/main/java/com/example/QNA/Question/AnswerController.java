package com.example.QNA.Question;

import com.example.QNA.global.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    public AnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @PostMapping("/create/{questionId}")
    public ApiResponse<AnswerResponseDTO> createAnswer(
            @RequestBody AnswerRequestDTO answerRequestDTO,
            @PathVariable("questionId") Long questionId) {

        Long answerId = answerService.createAnswer(answerRequestDTO, questionId);

        AnswerResponseDTO responseDTO = new AnswerResponseDTO();
        responseDTO.setId(answerId);
        responseDTO.setQuestionTitle(answerRequestDTO.getQuestionTitle());
        responseDTO.setContents(answerRequestDTO.getContents());
        responseDTO.setQuestionId(questionId);

        return new ApiResponse<>(200, "답변이 정상적으로 생성되었습니다.", responseDTO);
    }

    @GetMapping("/{questionId}")
    public ApiResponse<AnswerResponseDTO> getAnswer(@PathVariable("questionId") Long questionId) {
        AnswerResponseDTO answerResponseDTO = answerService.readAnswer(questionId);
        return new ApiResponse<>(200, "답변을 정상적으로 조회하였습니다.", answerResponseDTO);
    }
}
