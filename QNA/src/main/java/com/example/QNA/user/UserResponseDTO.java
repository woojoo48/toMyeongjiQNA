package com.example.QNA.user;

import com.example.QNA.studentclub.StudentClub;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String userId;
    private String userName;
    private String password;
    private String email;
    private String studentClubName;
    private String collegeName;
    private Role role;
}
