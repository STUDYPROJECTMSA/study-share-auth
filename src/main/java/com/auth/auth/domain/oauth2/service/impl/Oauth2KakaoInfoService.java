package com.auth.auth.domain.oauth2.service.impl;

import com.auth.auth.domain.oauth2.dto.Authorization;
import com.auth.auth.domain.oauth2.dto.LoginBody;
import com.auth.auth.domain.oauth2.service.Oauth2KakaoInfo;
import com.auth.auth.global.config.KakaoLoginApiProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@EnableConfigurationProperties({KakaoLoginApiProperties.class})
@RequiredArgsConstructor
public class Oauth2KakaoInfoService {
    private final KakaoLoginApiProperties kakaoLoginApiProperties;
    private final Oauth2KakaoInfo oauth2KakaoInfo;

    public Authorization getKakaoAccessToken(String code) {
        LoginBody loginBody = new LoginBody();
        loginBody.setCode(code);
        loginBody.setClient_id(kakaoLoginApiProperties.clientId);
        loginBody.setGrant_type(kakaoLoginApiProperties.authorizationGrantType);
        loginBody.setRedirect_uri(kakaoLoginApiProperties.redirectUri);

        return oauth2KakaoInfo.getAccessToken(loginBody);
    }

    public Map<String, Object> getKakaoUserInfo(String accessToken) {
        return oauth2KakaoInfo.getKakaoUserInfo("Bearer" + accessToken);
    }


}
