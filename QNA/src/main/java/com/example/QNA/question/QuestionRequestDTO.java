package com.example.QNA.question;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequestDTO {

    private Long id;
    private String title;
    private String content;
}
