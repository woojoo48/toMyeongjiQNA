package com.example.QNA.anwer;

import com.example.QNA.question.Question;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String answerTitle;
    @Column(columnDefinition = "TEXT")
    private String contents;

    @OneToOne
    private Question question;
}
