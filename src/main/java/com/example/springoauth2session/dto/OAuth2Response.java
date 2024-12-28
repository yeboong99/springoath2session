package com.example.springoauth2session.dto;

public interface OAuth2Response {

    String getProvider();   // OAuth2 제공자 이름 (네이버, 구글 등)

    String getProviderId(); // 제공자가 발급해주는 ID(번호, 식별자)

    String getEmail();  // 사용자 이메일

    String getName();   // 사용자 이름 (사용자가 설정한 이름)
}
