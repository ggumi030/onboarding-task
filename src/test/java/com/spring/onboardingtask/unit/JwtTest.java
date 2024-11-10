package com.spring.onboardingtask.unit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.spring.onboardingtask.user.entity.Authority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import com.spring.onboardingtask.global.config.JwtConfig;
import com.spring.onboardingtask.global.jwt.JwtService;
import com.spring.onboardingtask.user.entity.User;
import java.security.Key;
import java.util.Base64;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class JwtTest {
    private JwtService jwtService = new JwtService();
    private User testUser;
    private static Key key;

    @BeforeAll
    public static void init() {
        byte[] bytes = Base64.getDecoder().decode("a2V2aW4xMjM0MTIzNDEyMzQxMjM0MTIzNDEyMzQxMjM0");
        key = Keys.hmacShaKeyFor(bytes);
    }

    private void testUserSetup() {
        testUser = User.builder()
            .username("ggumi")
            .password("ggumi1234")
            .nickname("ggu")
            .authorities(List.of(Authority.builder()
                .authorityName("ROLE_USER")
                .build()))
            .build();
    }

    @Test
    @DisplayName("토큰 생성 테스트")
    public void test_generateToken() {
        testUserSetup();
        Long tokenTime = 180000L;
        String token = jwtService.createToken(testUser, tokenTime, key);
        assertThat(token, notNullValue());
    }

    @Test
    @DisplayName("토큰 검증 성공 테스트")
    public void success_isValidToken() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        testUserSetup();
        Long tokenTime = 180000L;
        String token = jwtService.createToken(testUser, tokenTime, key);

        boolean answer = jwtService.isValidToken(token.replace(JwtConfig.BEARER_PREFIX, ""), request, key);
        assertEquals(answer,true);
    }

    @Test
    @DisplayName("토큰 검증 예외 테스트(토큰 시간 만료)")
    public void failure_isValidToken() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        testUserSetup();
        Long tokenTime = 1L;
        String token = jwtService.createToken(testUser, tokenTime, key);

        jwtService.isValidToken(token.replace(JwtConfig.BEARER_PREFIX, ""), request, key);
        assertEquals((String)request.getAttribute("error"), "Expired JWT token, 만료된 JWT token 입니다.");
    }

    @Test
    @DisplayName("토큰 조회 테스트")
    public void test_getToken() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        testUserSetup();
        Long tokenTime = 180000L;
        String token = jwtService.createToken(testUser, tokenTime, key);
        request.addHeader("Authorization",token);

        String testToken = jwtService.getToken(request);
        assertEquals("Bearer " + testToken, token);
    }

    @Test
    @DisplayName("Claims 조회 테스트")
    public void test_getClaims() {
        testUserSetup();
        Long tokenTime = 180000L;
        String token = jwtService.createToken(testUser, tokenTime, key);

        Claims claims = jwtService.getClaims(token.replace(JwtConfig.BEARER_PREFIX, ""), key);
        assertThat(claims, notNullValue());
    }

}
