package com.example.QNA.validate;

import com.example.QNA.user.User;
import com.example.QNA.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId));

        return createUserDetails(user);
    }

    // User 엔티티를 Spring Security의 UserDetails로 변환
    //왜 변환하는가? -> security는 UserDetails형태로 인식 그러나 user의 엔티티를 그대로 주면 읽어들일수 없음. 그래서 변환
    private UserDetails createUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserId())
                .password(user.getPassword())
                .authorities(getAuthorities(String.valueOf(user.getRole())))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    // 사용자의 역할을 Spring Security 권한으로 변환
    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // DB의 role 값에 따라 권한 부여
        switch (role.toUpperCase()) {
            case "ADMIN":
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                break;
            case "PRESIDENT":
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));  // 회장도 관리자 권한
                break;
            case "MANAGER":
                authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
                break;
            case "MEMBER":
            default:
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                break;
        }

        return authorities;
    }
}