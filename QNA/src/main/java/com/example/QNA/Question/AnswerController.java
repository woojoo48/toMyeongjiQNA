package com.example.QNA.Question;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AnswerResponseDTO> createAnswer(
            @RequestBody AnswerRequestDTO answerRequestDTO,
            @PathVariable("questionId") Long questionId) {
        // 답변 생성 후 ID 반환
        Long answerId = answerService.createAnswer(answerRequestDTO, questionId);

        AnswerResponseDTO responseDTO = new AnswerResponseDTO();
        responseDTO.setId(answerId);
        responseDTO.setQuestionTitle(answerRequestDTO.getQuestionTitle());
        responseDTO.setContents(answerRequestDTO.getContents());
        responseDTO.setQuestionId(questionId);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<AnswerResponseDTO> getAnswer(@PathVariable("questionId") Long questionId) {
        AnswerResponseDTO answerResponseDTO = answerService.readAnswer(questionId);
        return ResponseEntity.ok(answerResponseDTO);
    }
}
