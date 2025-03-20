package com.example.QNA.user;

import com.example.QNA.studentclub.StudentClub;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class UserRequestDTO {
    private Long id;
    private String userName;
    private StudentClub studentClub;
}
