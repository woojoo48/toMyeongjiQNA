package com.example.QNA.Question;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerRequestDTO {
    private Long id;
    private String questionTitle;
    private String contents;
}
