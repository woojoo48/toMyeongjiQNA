package com.example.QNA.user;

import com.example.QNA.global.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ApiResponse<UserResponseDTO> signUpUser(@RequestBody UserSignUpRequestDTO userSignUpRequestDTO) {
        userService.signUpUser(userSignUpRequestDTO);
        UserResponseDTO responseDTO = userService.readOneUser(userSignUpRequestDTO.getUserName());
        return new ApiResponse<>(200, "유저가 성공적으로 생성되었습니다.", responseDTO);
    }

    @PostMapping("/login")
    public ApiResponse<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        UserLoginResponseDTO responseDTO = userService.login(userLoginRequestDTO);
        return new ApiResponse<>(200, "로그인이 성공하였습니다.", responseDTO);
    }

    @GetMapping("/{username}")
    public ApiResponse<UserResponseDTO> getUser(@PathVariable("username") String username) {
        UserResponseDTO responseDTO = userService.readOneUser(username);
        return new ApiResponse<>(200,"유저를 성공적으로 조회하였습니다.", responseDTO);
    }

    @GetMapping("/all")
    public ApiResponse<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.readAllUsers();
        return new ApiResponse<>(200,"유저목록을 성공적으로 조회하였습니다.", users);
    }
}