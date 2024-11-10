package com.spring.onboardingtask.global.jwt;

import com.spring.onboardingtask.global.config.JwtConfig;
import com.spring.onboardingtask.user.entity.Authority;
import com.spring.onboardingtask.user.entity.User;
import com.spring.onboardingtask.user.eums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtService {

    public String createToken(User user, Long tokenTime, Key key) {
        Date date = new Date();

        String username = user.getUsername();
        List<Authority> auth = user.getAuthorities();

        return JwtConfig.BEARER_PREFIX +
            Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(date.getTime() + tokenTime))
                .claim(JwtConfig.AUTHORIZATION_KEY, auth)
                .setIssuedAt(date)
                .signWith(key, JwtConfig.signatureAlgorithm)
                .compact();
    }

    public boolean isValidToken(String token, HttpServletRequest request, Key key) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException |
                 io.jsonwebtoken.security.SignatureException e) {
            request.setAttribute("error", "Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            request.setAttribute("error", "Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("error", "Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }

        return false;

    }

    public String getToken(HttpServletRequest request) {

        String token = request.getHeader(JwtConfig.AUTHORIZATION_HEADER);

        if (StringUtils.hasText(token) && token.startsWith(JwtConfig.BEARER_PREFIX)) {
            return token.replace(JwtConfig.BEARER_PREFIX, "");
        }
        return null;

    }

    public Claims getClaims(String token, Key key) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            .getBody();

    }
}
