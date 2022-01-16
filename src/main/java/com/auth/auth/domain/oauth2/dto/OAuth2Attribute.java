package com.auth.auth.domain.oauth2.dto;

import com.auth.auth.domain.user.domain.Role;
import com.auth.auth.domain.user.dto.UserDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OAuth2Attribute {
    private String oauth2Key;
    private String email;
    private String name;
    private String imageUrl;

    @Builder
    public OAuth2Attribute(String oauth2Key, String email, String name, String imageUrl) {
        this.oauth2Key = oauth2Key;
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static OAuth2Attribute of(String oauth2Key, Map<String, Object> attributes) {
        //나중에 혹시 naver 를 추가할수도있으니 추가
        log.info("요청 :: " + oauth2Key);
        log.info("속성 :: " + attributes);
        return ofKakao(attributes);
    }
    private static OAuth2Attribute ofKakao(
                                           Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2Attribute.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .imageUrl((String) kakaoProfile.get("profile_image_url"))
                .build();
    }
    public UserDto toDto(){
        UserDto userDto = new UserDto();
        userDto.setName(name);
        userDto.setEmail(email);
        userDto.setRole(Role.USER);
        userDto.setImageUrl(imageUrl);
        return userDto;
    }

}
