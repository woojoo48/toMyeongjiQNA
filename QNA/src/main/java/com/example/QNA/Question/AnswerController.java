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
        answerService.createAnswer(answerRequestDTO, questionId);
        return ResponseEntity.ok(answerService.readAnswer(questionId));
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<AnswerResponseDTO> getAnswer(@PathVariable("questionId") Long questionId) {
        AnswerResponseDTO answerResponseDTO = answerService.readAnswer(questionId);
        return ResponseEntity.ok(answerResponseDTO);
    }
}
