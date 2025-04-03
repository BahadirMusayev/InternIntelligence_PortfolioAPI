package com.example.internintelligenceportfolioapi.service.auth;

import com.example.internintelligenceportfolioapi.dao.entity.UserEntity;
import com.example.internintelligenceportfolioapi.dao.exception.FoundException;
import com.example.internintelligenceportfolioapi.dao.exception.NotFoundException;
import com.example.internintelligenceportfolioapi.dao.exception.PasswordException;
import com.example.internintelligenceportfolioapi.dao.repository.UserRepository;
import com.example.internintelligenceportfolioapi.mapper.UserMapper;
import com.example.internintelligenceportfolioapi.model.auth.AuthenticationDto;
import com.example.internintelligenceportfolioapi.model.input.UserLoginDtoInput;
import com.example.internintelligenceportfolioapi.model.input.UserRegistrationDtoInput;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserAuthService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @Transactional
    public void signUp(UserRegistrationDtoInput userRegistrationDto) {
        log.info("Sign Up Started... ");

        if (userRepository.findByEmail(userRegistrationDto.getEmail()) != null) {
            throw new FoundException("This email is already registered !");
        }

        var user = UserRegistrationDtoInput.builder()
                .name(userRegistrationDto.getName())
                .surname(userRegistrationDto.getSurname())
                .birthDate(userRegistrationDto.getBirthDate())
                .email(userRegistrationDto.getEmail())
                .password(passwordEncoder.encode(userRegistrationDto.getPassword()))
                .build();

        UserEntity userEntity = userMapper.mapRegistrationDtoInputToEntity(user);
        userRepository.save(userEntity);

        log.info("Sign Up Ended ");
    }

    public AuthenticationDto login(UserLoginDtoInput userLoginDto) {
        log.info("Login Started... ");

        UserEntity user = userRepository.findByEmail(userLoginDto.getEmail());
        if (user == null) {
            throw new NotFoundException("User Not Found !");
        }
        if (passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword())
            );
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationDto.builder()
                    .token(jwtToken)
                    .build();
        } else {
            throw new PasswordException("Sorry, your password was incorrect.");
        }
    }

    public static UserEntity getUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
