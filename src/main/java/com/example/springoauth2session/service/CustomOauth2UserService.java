package com.example.springoauth2session.service;

import com.example.springoauth2session.dto.CustomOAuth2User;
import com.example.springoauth2session.dto.GoogleResponse;
import com.example.springoauth2session.dto.NaverResponse;
import com.example.springoauth2session.dto.OAuth2Response;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService { // DefaultOauth2UserService는 Oauth2UserService(인터페이스)의 구현체이다.

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
        }

        // user role 생성 (우선 하드코딩)
        String role = "ROLE_USER";
        return new CustomOAuth2User(oAuth2Response, role);
    }


}
