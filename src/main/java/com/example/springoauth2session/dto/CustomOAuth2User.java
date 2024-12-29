package com.example.springoauth2session.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final OAuth2Response oAuth2Response;
    private final String role;

    @Override
    public Map<String, Object> getAttributes() {
        // attribute는 리소스 서버로부터 넘어오는 모든 데이터값들을 의미한다.
        // 또한 플랫폼마다 응답 객체 내의 key 등의 형태가 달라  이에 맞게 작성해야 한다.
        return null;
    }

    // role 반환해주는 함수
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role;
            }
        });


        return collection;
    }

    // 이름 반환해주는 함수
    @Override
    public String getName() {
        return oAuth2Response.getName();
    }

    public String getUsername() {

        return oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
    }
}
