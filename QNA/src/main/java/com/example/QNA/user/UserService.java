package com.example.QNA.user;

import com.example.QNA.global.CustomException;
import com.example.QNA.studentclub.StudentClub;
import com.example.QNA.studentclub.StudentClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import static com.example.QNA.global.ErrorMSG.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final StudentClubRepository studentClubRepository;

    public UserService(UserRepository userRepository, StudentClubRepository studentClubRepository) {
        this.userRepository = userRepository;
        this.studentClubRepository = studentClubRepository;
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

    //DTO객체 변환
    private UserResponseDTO convertToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUserName());
        dto.setRole(user.getRole());

        Optional.ofNullable(user.getStudentClub())
                .ifPresent(club -> {
                    dto.setStudentClubName(club.getStudentClubName());
                    Optional.ofNullable(club.getCollege())
                            .ifPresent(college -> dto.setCollegeName(college.getCollegeName()));
                });

        return dto;
    }
    @Transactional(readOnly = true)
    public UserResponseDTO readOneUser(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new CustomException(400, NOT_USER_NAME);
        }

        return userRepository.findByUserName(username)
                .map(this::convertToDTO)
                .orElseThrow(() -> new CustomException(400, "사용자를 찾을 수 없습니다: " + username));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> readAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}

