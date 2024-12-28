package com.example.springoauth2session.dto;

import java.util.Map;

public class GoogleResponse implements OAuth2Response{

    // 구글 OAuth2 클라이언트가 응답해주는 데이터 형태가 String, Object(JSON) 형태임.
    private final Map<String, Object> attribute;

    public GoogleResponse(Map<String, Object> attribute) {  // google은 naver와 다르게 따로 json데이터인 값에 접근하기 위해 response라는 키를 찾을 필요가 없는 것으로 생각됨.
        this.attribute = attribute;                         // 따라서 @RequiredArgsConstructor 어노테이션을 사용해도 무방할 것으로 예상되긴 함.
    }                                                       // 개발자 유미 문서 참고 - naver에서 보내주는 응답 형태와 google이 보내주는 응답 형태가 다름.
                                                            // 어떤 플랫폼에서 OAuth2 서비스를 받느냐에 따라 공식문서를 확인해야 함

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();
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
