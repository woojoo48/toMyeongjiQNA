package com.example.QNA.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        userService.createUser(userRequestDTO);
        UserResponseDTO responseDTO = userService.readOneUser(userRequestDTO.getUserName());
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable("username") String username) {
        UserResponseDTO responseDTO = userService.readOneUser(username);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.readAllUsers();
        return ResponseEntity.ok(users);
    }
}