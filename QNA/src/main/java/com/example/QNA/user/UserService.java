package com.example.QNA.user;

import com.example.QNA.global.CustomException;
import com.example.QNA.studentclub.StudentClub;
import com.example.QNA.studentclub.StudentClubRepository;
import com.example.QNA.mapper.QNAMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.QNA.global.ErrorMSG.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final StudentClubRepository studentClubRepository;
    private final QNAMapper qnaMapper;

    public UserService(UserRepository userRepository, StudentClubRepository studentClubRepository, QNAMapper qnaMapper) {
        this.userRepository = userRepository;
        this.studentClubRepository = studentClubRepository;
        this.qnaMapper = qnaMapper;
    }

    @Transactional
    public void createUser(UserRequestDTO userRequestDTO) {
        String userId = userRequestDTO.getUserId();
        String username = userRequestDTO.getUserName();
        String role = userRequestDTO.getRole();
        Long studentClubId = userRequestDTO.getStudentClubId();

        if (userId == null || userId.trim().isEmpty()) {
            throw new CustomException(400, NOT_USER_ID);
        }

        if (username == null || username.trim().isEmpty()) {
            throw new CustomException(400, NOT_USER_NAME);
        }

        if (role == null || role.trim().isEmpty()) {
            throw new CustomException(400, NOT_ROLE);
        }

        if (studentClubId == null) {
            throw new CustomException(400, NOT_STUDENT_CLUB);
        }

        StudentClub studentClub = studentClubRepository.findById(studentClubId)
                .orElseThrow(() -> new CustomException(400, NOT_STUDENT_CLUB + " ID: " + studentClubId));

        User userEntity = new User();
        userEntity.setUserId(userId);
        userEntity.setUserName(username);
        userEntity.setStudentClub(studentClub);
        userEntity.setRole(role);

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

