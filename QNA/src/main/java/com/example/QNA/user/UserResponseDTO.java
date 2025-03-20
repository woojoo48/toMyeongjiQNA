package com.example.QNA.user;

import com.example.QNA.studentclub.StudentClub;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String userName;
    private StudentClub studentClub;
}
