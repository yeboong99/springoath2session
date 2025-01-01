package com.example.springoauth2session.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
@RequiredArgsConstructor
public class CustomClientRegistrationRepo {

    // 1. 소셜로그인 설정정보를 주입받아
    private final SocialClientRegistration socialClientRegistration;

    // 2. 인메모리 방식으로 관리를 진행한다. (이름 또는 프로필, 이메일 정도의 정보만 사용하므로)
    // 관리할 정보가 많아지면 jdbc로 db에 연결해서 저장하는 방식을 사용한다.
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(socialClientRegistration.naverClientRegistration(), socialClientRegistration.googleClientRegistraion());
    }
}