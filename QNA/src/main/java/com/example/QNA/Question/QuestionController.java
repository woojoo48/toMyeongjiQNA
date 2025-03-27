package com.example.QNA.Question;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<QuestionResponseDTO> createQuestion(@RequestBody QuestionRequestDTO dto) {
        Long newQuestionId = questionService.createQuestion(dto);
        return ResponseEntity.ok(questionService.readOneQuestion(newQuestionId));
    }

    //질문 번호로 주소 찍어야 함
    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> getQuestion(@PathVariable("id") Long id) {
        QuestionResponseDTO question = questionService.readOneQuestion(id);
        return ResponseEntity.ok(question);
    }

    @GetMapping("/list")
    public ResponseEntity<List<QuestionResponseDTO>> getAllQuestions() {
        List<QuestionResponseDTO> questions = questionService.readAllQuestion();
        return ResponseEntity.ok(questions);
    }
}