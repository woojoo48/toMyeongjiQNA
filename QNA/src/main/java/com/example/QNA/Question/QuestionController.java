package com.example.QNA.Question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/question/create")
    public String createQuestion(){
        return "question/create";
    }

    @PostMapping("/question/create")
    public String createQuestionProcess(QuestionRequestDTO dto){
        questionService.createQuestion(dto);
        return "redirect:/question/create";
    }

    @GetMapping("/question/read/{id}")
    public String readQuestion(Model model, @PathVariable("id") long id){
        model.addAttribute("Question", questionService.readOneQuestion(id));

        return "question/read";
    }
    //모든 질문 읽기
    @GetMapping("/question/list")
    public String readAllQuestions(Model model){
        model.addAttribute("questions", questionService.readAllQuestion());
        return "question/list";
    }
}
