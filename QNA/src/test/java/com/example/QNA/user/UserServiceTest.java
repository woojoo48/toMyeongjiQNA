package com.example.QNA.user;

import com.example.QNA.studentclub.StudentClub;
import com.example.QNA.studentclub.StudentClubRepository;
import com.example.QNA.mapper.QNAMapper;
import com.example.QNA.validate.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private StudentClubRepository studentClubRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private QNAMapper qnaMapper;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    @Test
    void 회원가입_성공() {
        UserSignUpRequestDTO requestDTO = new UserSignUpRequestDTO();
        requestDTO.setUserId("testuser");
        requestDTO.setUserName("테스트유저");
        requestDTO.setPassword("password123");
        requestDTO.setEmail("test@example.com");
        requestDTO.setRole(Role.MEMBER);
        requestDTO.setStudentClubId(1L);

        StudentClub studentClub = StudentClub.builder()
                .id(1L)
                .studentClubName("컴퓨터공학과")
                .build();

        when(userRepository.existsByUserId("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(studentClubRepository.findById(1L)).thenReturn(Optional.of(studentClub));
        when(passwordEncoder.encode("password123")).thenReturn("암호화된비밀번호");

        assertDoesNotThrow(() -> userService.signUpUser(requestDTO));

        verify(userRepository).save(any(User.class));
    }
}