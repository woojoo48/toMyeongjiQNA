package com.example.QNA.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/create")
    public String createUser(){
        return "create";
    }

    @PostMapping("/user/create")
    public String createProcess(UserRequestDTO userRequestDTO){
        userService.createUser(userRequestDTO);
        return "redirect:/user/create";
    }

}
