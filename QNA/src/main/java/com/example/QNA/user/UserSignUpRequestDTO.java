package com.example.QNA.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpRequestDTO {
    private String userId;
    private String userName;
    private String password;
    private String email;
    private Role role;
    private Long studentClubId;
}
