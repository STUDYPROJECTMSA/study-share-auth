package com.auth.auth.user.service.impl;

import com.auth.auth.oauth2.dto.Authorization;
import com.auth.auth.oauth2.dto.OAuth2Attribute;
import com.auth.auth.oauth2.dto.SecurityToken;
import com.auth.auth.oauth2.service.SecurityTokenService;
import com.auth.auth.oauth2.service.impl.Oauth2KakaoInfoService;
import com.auth.auth.user.domain.Role;
import com.auth.auth.user.domain.User;
import com.auth.auth.user.dto.UserDto;
import com.auth.auth.user.repository.UserRepository;
import com.auth.auth.user.service.UserService;
import com.auth.auth.util.mapper.UserMapper;
import com.auth.auth.util.redis.RedisService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final SecurityTokenService securityTokenService;
    private final Oauth2KakaoInfoService oauth2KakaoInfo;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RedisService redisService;

    @Override
    public SecurityToken refreshService(String expiredAccessToken, String refreshToken) throws Exception {
        String email = null;
        if(expiredAccessToken.isEmpty() || refreshToken.isEmpty()){
            throw new Exception();
        }

        try {
            email = securityTokenService.tokenGetEmail(expiredAccessToken);
        } catch (ExpiredJwtException e) {
            email = e.getClaims().getSubject();
            log.info("email from expired access token: " + email);
        }

        if (email == null) throw new IllegalArgumentException();
        String refreshTokenFromRedis = redisService.findByEmail(email);
        //TODO: gateway 에서 처리하기
        if (!refreshToken.equals(refreshTokenFromRedis)) {
            log.info("\"refresh is not equal\"");

        }
        if (securityTokenService.checkExpiredRefreshToken(refreshToken)) {
            log.info("refresh token is expried go to loginpage");
        }

        SecurityToken newToken = securityTokenService.generateToken(email, Role.USER.name());
        return newToken;
    }

    @Override
    public UserDto loginService(String code) {
        Map<String, Object> userInfo = null;
        Authorization authorization = oauth2KakaoInfo.getKakaoAccessToken(code);
        userInfo = oauth2KakaoInfo.getKakaoUserInfo(authorization.getAccess_token());
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of("kakao", userInfo);
        UserDto userDto = oAuth2Attribute.toDto();
        User user = saveOrUpdate(userDto);

        SecurityToken securityToken = securityTokenService.generateToken(userDto.getEmail(), Role.USER.name());

        redisService.saveToken(user.getEmail() + '_', securityToken.getToken());
        UserDto userDtoResponse = userMapper.toDto(user);
        userDtoResponse.setToken(securityToken.getToken());
        userDtoResponse.setRefreshToken(securityToken.getRefreshToken());

        return userDtoResponse;
    }

    private User saveOrUpdate(UserDto userDto) {
        User user = userRepository.findByemail(userDto.getEmail())
                .map(entity -> entity.update(userDto.getName(), userDto.getImageUrl()))
                .orElse(userMapper.toEntity(userDto));
        return userRepository.save(user);
    }
}
