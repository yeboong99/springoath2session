package com.example.springoauth2session.service;

import com.example.springoauth2session.dto.CustomOAuth2User;
import com.example.springoauth2session.dto.GoogleResponse;
import com.example.springoauth2session.dto.NaverResponse;
import com.example.springoauth2session.dto.OAuth2Response;
import com.example.springoauth2session.entity.User;
import com.example.springoauth2session.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService { // DefaultOauth2UserService는 Oauth2UserService(인터페이스)의 구현체이다.

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);    // 부모 클래스의 loadUser()로 받은 요청을 전달하여 OAuth2User 객체를 얻는다.
        System.out.println(oAuth2User.getAttributes());

        // OAuth2 제공처가 네이버인지 구글인지를 구분해줄 registrationId변수 설정. getRegistraionId()메서드를 통해 얻어올 수 있다.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2Response를 부모 인터페이스로, NaverResponse와 GoogleResponse를 자식클래스으로 갖는 응답받을 DTO를 작성해둔다.
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else {
            return null;
        }   // 여기까지 오면 naver 또는 google에서 받은 응답을 oAuth2Response에 추출한 상태.

        // username은 제공자 + 공백 + 제공자ID 형태로 이루어져 있음
        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        User existData = userRepository.findByUsername(username);

        String role = null;

        if (existData == null) {    // 저장된 ID가 없다면 새로 생성하여 저장
            User user = new User();
            user.setUsername(username);
            user.setEmail(oAuth2Response.getEmail());
            user.setRole("ROLE_USER");

            userRepository.save(user);
        }
        else {                    // 이미 저장된 ID가 검색된다면 새로 받은 정보로 업데이트

            existData.setUsername(username);
            existData.setEmail(oAuth2Response.getEmail());

            role = existData.getRole();

            userRepository.save(existData);
        }

        return new CustomOAuth2User(oAuth2Response, role);
    }

}
