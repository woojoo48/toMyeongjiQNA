package com.example.QNA.question;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionResponseDTO {
    private Long id;
    private String title;
    private String contents;
    private Long userId;
    private String userName;
    private boolean hasAnswer;
}
