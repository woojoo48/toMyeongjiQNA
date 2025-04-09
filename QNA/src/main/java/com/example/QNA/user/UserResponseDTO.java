package com.example.QNA.user;

import com.example.QNA.studentclub.StudentClub;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private String userId;
    private String userName;
    private String studentClubName;
    private String collegeName;
    private String role;
}
