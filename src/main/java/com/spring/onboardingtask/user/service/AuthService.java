package com.spring.onboardingtask.user.service;

import com.spring.onboardingtask.global.exception.CustomException;
import com.spring.onboardingtask.user.dto.SignupReqDto;
import com.spring.onboardingtask.user.dto.SignupResDto;
import com.spring.onboardingtask.user.entity.Authority;
import com.spring.onboardingtask.user.entity.User;
import com.spring.onboardingtask.user.eums.UserRole;
import com.spring.onboardingtask.user.mapper.UserMapper;
import com.spring.onboardingtask.user.repository.AuthorityRepository;
import com.spring.onboardingtask.user.repository.UserRepository;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public SignupResDto signup(SignupReqDto signupReqDto) {
        String username = signupReqDto.getUsername();
        String nickname = signupReqDto.getNickname();
        String password = passwordEncoder.encode(signupReqDto.getPassword());

        if (userRepository.findByUsername(username).isPresent()) {
            throw new CustomException(messageSource.getMessage(
                "already.exist.username",
                null,
                "Already exist username",
                Locale.getDefault()
            ), HttpStatus.CONFLICT);
        }

        Authority auth = authorityRepository.findByAuthorityName("ROLE_" + UserRole.USER.toString())
            .orElseThrow(() -> new CustomException(messageSource.getMessage(
                "authority.not.found",
                null,
                "Authority not found",
                Locale.getDefault()
            ), HttpStatus.NOT_FOUND));

        User newUser = User.builder()
            .username(username)
            .nickname(nickname)
            .password(password)
            .authorities(List.of(auth))
            .build();

        userRepository.save(newUser);

        return UserMapper.toSignupResDto(newUser);
    }


}
