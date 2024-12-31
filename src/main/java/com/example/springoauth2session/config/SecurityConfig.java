package com.example.springoauth2session.config;

import com.example.springoauth2session.service.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOauth2UserService customOauth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrf) -> csrf.disable());
        http
                .formLogin((login) -> login.disable());
        http
                .httpBasic((basic) -> basic.disable());

        // oauth2Login vs oauth2Client 차이점
        // Login은 oath2 인증 서버, 리소스 서버를 통과하며 유저정보를 받아오기까지의 로직을 자동으로 처리해주고,
        // Client는 해당 과정 중의 로직들을 전부 커스텀 구현해 줘야 함.
        // 이 실습에서는 Login을 이용함.
        http
                .oauth2Login((oauth2) -> oauth2
                        // login controller로 커스텀한 로그인 페이지로 요청할 수 있도록 설정
                        .loginPage("/login")
                        // userInfoEndpoint : 데이터를 받을 수 있는 UserDetailsService를 등록해주는 엔드포인트
                        .userInfoEndpoint((userInfoEndpointConfig) ->
                                userInfoEndpointConfig.userService(customOauth2UserService)));  // 커스텀해둔 customOauth2UserService를 등록

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/oauth2/**", "/login/**").permitAll()    // 이 경로 요청은 모두 허용
                        .anyRequest().authenticated());     // 나머지 경로는 로그인 한 사용자만 이용 가능




        return http.build();
    }

}
