package com.spring.onboardingtask.unit;

import com.spring.onboardingtask.global.jwt.JwtService;
import com.spring.onboardingtask.user.entity.User;
import com.spring.onboardingtask.user.eums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class JwtTest {
    private JwtService jwtService = new JwtService();
    private User testUser;

    private void testUserSetup() {
        testUser = User.builder()
            .username("ggumi")
            .password("ggumi1234")
            .nickname("ggu")
            .authorityName(UserRole.USER)
            .build();
    }

    @Test
    @DisplayName("토큰 생성 테스트")
    public void test_generateToken() {
        testUserSetup();
        Long tokenTime = 180000L;
        //String token = jwtService.createToken(testUser, tokenTime);
        //assertThat(token, notNullValue());
    }

    @Test
    @DisplayName("토큰 검증 성공 테스트")
    public void success_isValidToken() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        testUserSetup();
        Long tokenTime = 180000L;
        //String token = jwtService.createToken(testUser, tokenTime);

        //boolean answer = jwtService.isValidToken(token, request);
        //assertThat(answer, true);
    }

    @Test
    @DisplayName("토큰 검증 예외 테스트(토큰 시간 만료)")
    public void failure_isValidToken() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        testUserSetup();
        Long tokenTime = 1L;
        //String token = jwtService.createToken(testUser, tokenTime);

        //jwtService.isValidToken(token, request);
        //asserThat((String)request.getAttribute("error"), "Expired JWT token, 만료된 JWT token 입니다.")
    }

    @Test
    @DisplayName("토큰 조회 테스트")
    public void test_getToken() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        testUserSetup();
        Long tokenTime = 180000L;
        //String token = jwtService.createToken(testUser, tokenTime);
        //request.addHeader("Authorization",token);

        //String testToken = getToken(request);
        //asserThat(testToken, token);
    }

    @Test
    @DisplayName("Claims 조회 테스트")
    public void test_getClaims() {
        testUserSetup();
        Long tokenTime = 180000L;
        //String token = jwtService.createToken(testUser, tokenTime);

        //String claims = getClaims(token);
        //asserThat(claims, notNullValue());
    }

}
