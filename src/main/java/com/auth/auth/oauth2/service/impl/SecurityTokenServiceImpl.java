package com.auth.auth.oauth2.service.impl;

import com.auth.auth.oauth2.dto.SecurityToken;
import com.auth.auth.oauth2.service.SecurityTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
public class SecurityTokenServiceImpl implements SecurityTokenService {
    @Value("${security.jwt.token.security-key:secret-key}")
    private String secretKey;
    private Date now = new Date();
    private Date tokenPeriod = DateUtils.addDays(now, 3);
    private Date refreshPeriod = DateUtils.addDays(now, 30);

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    @Override
    public SecurityToken generateToken(String email, String role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);
        return new SecurityToken(
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(tokenPeriod)
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact(),
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(refreshPeriod)
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact()
        );
    }

    @Override
    public String tokenGetEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    @Override
    public boolean checkExpiredRefreshToken(String refreshToken) {
        Claims parseInfo = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken).getBody();
        boolean isExpired = parseInfo.getExpiration().before(new Date());
        return isExpired;    }
}
