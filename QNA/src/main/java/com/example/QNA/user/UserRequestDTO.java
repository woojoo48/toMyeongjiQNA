package com.example.QNA.user;

import com.example.QNA.studentclub.StudentClub;
import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private String userId;
    private String userName;
    private String password;
    private String email;
    private Long studentClubId;
    private Role role;
}
