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
        questionService.createQuestion(dto);
        // 일반적으로 생성된 리소스의 ID를 반환하거나, 생성된 리소스 자체를 반환합니다
        // 여기서는 간단히 dto의 id를 사용하고 있지만, 실제로는 생성된 Question의 ID를 사용해야 합니다
        return ResponseEntity.ok(questionService.readOneQuestion(dto.getId()));
    }

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