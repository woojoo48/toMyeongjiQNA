package com.example.QNA.config;

import com.example.QNA.validate.JwtAuthenticationFilter;
import com.example.QNA.validate.UserDetailServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailServiceImpl userDetailService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화-JWT 사용 시 불필요
                .csrf(AbstractHttpConfigurer::disable)
//                // CORS 설정
//                //프론트에서 백엔드 서버를 호출할 수 있도록 연결
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 세션 사용하지 않음 -> stateless = 세션 사용X 설정
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                //인증 설정
                .authorizeHttpRequests(auth -> auth
                        // 인증 없이 접근 가능한 경로
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/user/sign-up").permitAll()
                        .requestMatchers("/api/user/login").permitAll()
                        .requestMatchers("/error").permitAll()

                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/answer/**").hasAnyRole("ADMIN")
                        //위에 정의한 것들을 제외하고는 모두 로그인 후 사용 가능하도록 설정
                        .anyRequest().authenticated())

                // 인증 제공자 - 로그인 검증 확인 담당자
                .authenticationProvider(authenticationProvider())

                // JWT 필터 설정 -> 토큰이 존재한다면 토큰으로 인증하고, 없다면 기본인증으로 전환하기 위한 필터
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    //스프링 시큐리티에서 지원하는 암호화 알고리즘을 구현해둔 라이브러리
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    //DB에서 사용자 정보를 불러오는 것
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
//공부
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
//
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//
//        configuration.setAllowedHeaders(Arrays.asList("*"));
//
//        configuration.setAllowCredentials(true);
//
//        configuration.setExposedHeaders(Arrays.asList("Authorization"));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
    // cors config  빼보기
}