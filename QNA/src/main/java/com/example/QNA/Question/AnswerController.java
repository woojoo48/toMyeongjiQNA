package com.example.QNA.Question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    public AnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @GetMapping("/answer/create")
    public String createAnswer(){
        return "answer/create";
    }

    @PostMapping("/answer/create")
    public String createQuestionProcess(QuestionRequestDTO dto){
        questionService.createQuestion(dto);
        return "redirect:/answer/create";
    }

    @GetMapping("/answer/read/{questionid}")
    public String readQuestion(Model model, @PathVariable("questionid") Long questionid){
        model.addAttribute("Question", questionService.readOneQuestion(questionid));
        return "answer/read";
    }
}
