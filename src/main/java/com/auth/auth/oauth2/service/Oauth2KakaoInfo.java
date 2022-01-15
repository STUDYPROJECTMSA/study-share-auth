package com.auth.auth.oauth2.service;

import com.auth.auth.oauth2.dto.Authorization;
import com.auth.auth.oauth2.dto.LoginBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Component
@FeignClient(name = "Oauth2KakaoInfoService",
        url = "${spring.security.oauth2.client.provider.kakao.uri}")
public interface Oauth2KakaoInfo {
    @RequestMapping(method = RequestMethod.POST,
            value = "${spring.security.oauth2.client.provider.kakao.token-uri}",
            produces = "application/json")
    Authorization getAccessToken(@RequestBody LoginBody loginBody);

    @RequestMapping(method = RequestMethod.POST,
            value = "${spring.security.oauth2.client.provider.kakao.user-info-uri}",
            produces = "application/json")
    Map<String, Object> getKakaoUserInfo(@RequestHeader("Authorization") String Authorization);
}
