package com.spring.onboardingtask.mvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.onboardingtask.global.config.WebSecurityConfig;
import com.spring.onboardingtask.user.controller.AuthController;
import com.spring.onboardingtask.user.dto.AuthDto;
import com.spring.onboardingtask.user.dto.LoginReqDto;
import com.spring.onboardingtask.user.dto.LoginResDto;
import com.spring.onboardingtask.user.dto.SignupReqDto;
import com.spring.onboardingtask.user.dto.SignupResDto;
import com.spring.onboardingtask.user.service.AuthService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = {AuthController.class},
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class
        )
    })
public class AuthControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .build();
    }

    @Test
    public void test_signup() throws Exception {
        SignupReqDto signupReqDto = SignupReqDto.builder()
            .username("ggumi")
            .password("1234")
            .nickname("ggu")
            .build();

        SignupResDto signupResDto = SignupResDto.builder()
            .username("ggumi")
            .nickname("ggu")
            .authorities(List.of(AuthDto.builder()
                .authorityName("ROLE_USER")
                .build()))
            .build();

        when(authService.signup(any(SignupReqDto.class))).thenReturn(signupResDto);

        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signupReqDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("ggumi"))
            .andExpect(jsonPath("$.nickname").value("ggu"))
            .andExpect(jsonPath("$.authorities[0].authorityName").value("ROLE_USER"));

    }

    @Test
    public void test_sign() throws Exception {
        LoginReqDto loginReqDto = LoginReqDto.builder()
            .username("ggumi")
            .password("1234")
            .build();

        LoginResDto loginResDto = LoginResDto.builder()
            .token("AWEIJFAWOEJFLWEK")
            .build();

        when(authService.login(any(LoginReqDto.class))).thenReturn(loginResDto);

        mockMvc.perform(post("/sign")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginReqDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("AWEIJFAWOEJFLWEK"));
    }

}
