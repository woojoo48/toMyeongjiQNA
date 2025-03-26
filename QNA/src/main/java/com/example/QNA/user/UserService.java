package com.example.QNA.user;

import com.example.QNA.studentclub.StudentClub;
import com.example.QNA.studentclub.StudentClubRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        String username = userRequestDTO.getUserName();
        Long studentClubId = userRequestDTO.getStudentClubId();

        StudentClub studentClub = studentClubRepository.findById(studentClubId).orElseThrow();

        User userEntity = new User();
        userEntity.setUserName(username);
        userEntity.setStudentClub(studentClub);
        userEntity.setRole(userRequestDTO.getRole());

        userRepository.save(userEntity);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO readOneUser(String username) {
        User userEntity = userRepository.findByUserName(username).orElseThrow();

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(userEntity.getId());
        dto.setUserName(userEntity.getUserName());
        dto.setRole(userEntity.getRole());

        if (userEntity.getStudentClub() != null) {
            dto.setStudentClubId(userEntity.getStudentClub().getId());
            dto.setStudentClubName(userEntity.getStudentClub().getStudentClubName());

            if (userEntity.getStudentClub().getCollege() != null) {
                dto.setCollegeName(userEntity.getStudentClub().getCollege().getCollegeName());
            }
        }
        return dto;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> readAllUsers() {
            List<User> userList = userRepository.findAll();

            List<UserResponseDTO> userListDTO = new ArrayList<>();
            for (User user : userList) {
                UserResponseDTO dtos = new UserResponseDTO();
                dtos.setId(user.getId());
                dtos.setUserName(user.getUserName());
                dtos.setRole(user.getRole());

                if (user.getStudentClub() != null) {
                    dtos.setStudentClubId(user.getStudentClub().getId());
                    dtos.setStudentClubName(user.getStudentClub().getStudentClubName());


                    if (user.getStudentClub().getCollege() != null) {
                        dtos.setCollegeName(user.getStudentClub().getCollege().getCollegeName());
                    }
                }
                userListDTO.add(dtos);
            }
            return userListDTO;
    }
}

