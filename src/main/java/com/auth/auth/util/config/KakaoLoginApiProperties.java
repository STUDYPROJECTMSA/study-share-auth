package com.auth.auth.util.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.kakao")
public class KakaoLoginApiProperties {
    public String clientId;
    public String redirectUri;
    public String authorizationGrantType;
}
