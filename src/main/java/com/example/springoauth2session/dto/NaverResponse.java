package com.example.springoauth2session.dto;

import lombok.RequiredArgsConstructor;

import java.util.Map;

public class NaverResponse implements OAuth2Response{

    // Naver Oauth2 클라이언트가 응답해주는 데이터의 형태가 String, Object(JSON) 형태임. 그 중에서도 리소스 데이터에 접근하려면 "response"라는 키에 대한 값을 얻어줘야 함.
    private final Map<String, Object> attribute;

    // 이 dto가 다룰 대상이 Object를 포함한 Map 타입이라 생성자 커스텀이 필요해서 @RequiredArgsConstructor 사용 없이 직접 생성자 만들어줬음
    public NaverResponse(Map<String, Object> attribute) {
        this.attribute = (Map<String, Object>) attribute.get("response");   // Naver가 제공하는 응답 형태를 보면 response라는 키에 접근하여 데이터를 꺼내야 하는 형태임.
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }
}
