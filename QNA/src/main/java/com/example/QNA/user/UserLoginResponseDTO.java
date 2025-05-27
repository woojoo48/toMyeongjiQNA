package com.example.QNA.user;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDTO {
    //토큰
    private String accessToken;
    @Builder.Default
    //왜 Default를 설정해주는가? -> 빌더 내부 동작때문
    //디폴트로 지정하지 않으면 토큰 타입이 null로 들어가게 된다.
    private String tokenType = "Bearer";
    private Long expiresIn;

    //data
    private String userId;
    private String userName;
    private String email;
    private String role;
}
