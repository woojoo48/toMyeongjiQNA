package com.example.QNA.Question;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequestDTO {

    private Long id; //userId
    private String title;
    private String content;
}
