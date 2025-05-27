package com.example.QNA.user;

import com.example.QNA.global.CustomException;
import com.example.QNA.studentclub.StudentClub;
import com.example.QNA.studentclub.StudentClubRepository;
import com.example.QNA.mapper.QNAMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.QNA.global.ErrorMSG.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final StudentClubRepository studentClubRepository;
    private final QNAMapper qnaMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUpUser(UserSignUpRequestDTO userSignUpRequestDTO) {
        String userId = userSignUpRequestDTO.getUserId();
        String username = userSignUpRequestDTO.getUserName();
        String password = userSignUpRequestDTO.getPassword();
        String email = userSignUpRequestDTO.getEmail();
        Role role = userSignUpRequestDTO.getRole();
        Long studentClubId = userSignUpRequestDTO.getStudentClubId();

        if (userId == null || userId.trim().isEmpty()) {
            throw new CustomException(400, NOT_USER_ID);
        } else if(userRepository.existsByUserId(userId)){
            throw new CustomException(400, EXIST_USER_ID);
        }

        if (username == null || username.trim().isEmpty()) {
            throw new CustomException(400, NOT_USER_NAME);
        }

        if(password == null || password.trim().isEmpty()){
            throw new CustomException(400, NOT_USER_PASSWORD);
        }

        if(email == null || email.trim().isEmpty()){
            throw new CustomException(400, NOT_USER_EMAIL);
        }else if(userRepository.existsByEmail(email)){
            throw new CustomException(400, EXIST_USER_EMAIL);
        }

        if (role == null) {
            throw new CustomException(400, NOT_ROLE);
        }

        if (studentClubId == null) {
            throw new CustomException(400, NOT_STUDENT_CLUB);
        }

        StudentClub studentClub = studentClubRepository.findById(studentClubId)
                .orElseThrow(() -> new CustomException(400, NOT_STUDENT_CLUB + " ID: " + studentClubId));

        User userEntity = User.builder()
                .userId(userId)
                .userName(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .studentClub(studentClub)
                .role(role)
                .build();

        userRepository.save(userEntity);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO readOneUser(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new CustomException(400, NOT_USER_NAME);
        }
        return userRepository.findByUserName(username)
                .map(qnaMapper::toUserDto)
                .orElseThrow(() -> new CustomException(400, "사용자를 찾을 수 없습니다: " + username));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> readAllUsers() {
        return userRepository.findAll().stream()
                .map(qnaMapper::toUserDto)
                .collect(Collectors.toList());
    }



}

