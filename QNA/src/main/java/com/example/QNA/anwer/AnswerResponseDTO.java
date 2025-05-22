package com.example.QNA.anwer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerResponseDTO {
    private Long id;
    private String answerTitle;
    private String contents;
    private Long questionId;
}
