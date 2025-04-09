package com.example.QNA.user;

import com.example.QNA.studentclub.StudentClub;
import com.example.QNA.studentclub.StudentClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentClubRepository studentClubRepository;

    @Transactional
    public void createUser(UserRequestDTO userRequestDTO) {
        String userId = userRequestDTO.getUserId();
        String username = userRequestDTO.getUserName();
        Long studentClubId = userRequestDTO.getStudentClubId();

        StudentClub studentClub = studentClubRepository.findById(studentClubId).orElseThrow();

        User userEntity = new User();
        userEntity.setUserId(userId);
        userEntity.setUserName(username);
        userEntity.setStudentClub(studentClub);
        userEntity.setRole(userRequestDTO.getRole());

        userRepository.save(userEntity);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO readOneUser(String username) {
        User userEntity = userRepository.findByUserName(username).orElseThrow();

        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(userEntity.getUserId());
        dto.setUserName(userEntity.getUserName());
        dto.setRole(userEntity.getRole());

        return dto;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> readAllUsers() {
            List<User> userList = userRepository.findAll();

            List<UserResponseDTO> userListDTO = new ArrayList<>();
            for (User user : userList) {
                UserResponseDTO dtos = new UserResponseDTO();
                dtos.setUserId(user.getUserId());
                dtos.setUserName(user.getUserName());
                dtos.setRole(user.getRole());


                userListDTO.add(dtos);
            }
            return userListDTO;
    }
}

