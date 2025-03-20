package com.example.QNA.user;

import com.example.QNA.studentclub.StudentClub;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void createUser(UserRequestDTO userRequestDTO) {
        String username = userRequestDTO.getUserName();
        StudentClub studentClub = userRequestDTO.getStudentClub();

        User userEntity = new User();
        userEntity.setUserName(username);
        userEntity.setStudentClub(studentClub);

        userRepository.save(userEntity);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO readOneUser(String username) {
        User userEntity = userRepository.findByUserName(username).orElseThrow();

        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserName(userEntity.getUserName());
        dto.setStudentClub(userEntity.getStudentClub());

        return dto;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> readAllUsers() {
        List<User> userList = userRepository.findAll();

        List<UserResponseDTO> userListDTO = new ArrayList<>();
        for (User user : userList){
            UserResponseDTO dto = new UserResponseDTO();
            dto.setUserName(user.getUserName());
            dto.setStudentClub(user.getStudentClub());

            userListDTO.add(dto);
        }
        return userListDTO;
    }
}
