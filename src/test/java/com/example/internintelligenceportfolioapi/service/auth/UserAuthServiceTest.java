package com.example.internintelligenceportfolioapi.service.auth;

import com.example.internintelligenceportfolioapi.dao.entity.UserEntity;
import com.example.internintelligenceportfolioapi.dao.repository.UserRepository;
import com.example.internintelligenceportfolioapi.mapper.UserMapper;
import com.example.internintelligenceportfolioapi.model.auth.AuthenticationDto;
import com.example.internintelligenceportfolioapi.model.input.UserLoginDtoInput;
import com.example.internintelligenceportfolioapi.model.input.UserRegistrationDtoInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAuthServiceTest {
    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authManager;

    @InjectMocks
    private UserAuthService userAuthService;

    @Test
    @Transactional
    void signUp() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setEmail("ali@example.com");

        String rawPassword = "string";
        String encodedPassword = "$2a$10$gEn9n7biN.TuvOvvXAy11OkVaAobZVYtDUZJZb4CKruLsMzfR3U8y";

        UserRegistrationDtoInput inputDto = new UserRegistrationDtoInput(
                "Ali",
                "Ali",
                LocalDate.of(1990, 1, 1),
                "ali@example.com",
                rawPassword
        );

        UserRegistrationDtoInput registrationDtoInput = new UserRegistrationDtoInput(
                "Ali",
                "Ali",
                LocalDate.of(1990, 1, 1),
                "ali@example.com",
                encodedPassword
        );

        UserEntity expectedEntity = new UserEntity();
        expectedEntity.setName("Ali");
        expectedEntity.setSurname("Ali");
        expectedEntity.setBirthDate(LocalDate.of(1990, 1, 1));
        expectedEntity.setEmail("ali@example.com");
        expectedEntity.setPassword(encodedPassword);

        when(userRepository.findByEmail(inputDto.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userMapper.mapRegistrationDtoInputToEntity(registrationDtoInput)).thenReturn(expectedEntity);

        userAuthService.signUp(inputDto);

        assertEquals(registrationDtoInput.getName(), expectedEntity.getName(), "Should change name !");
        assertEquals(registrationDtoInput.getSurname(), expectedEntity.getSurname(), "Should change surname !");
        assertEquals(registrationDtoInput.getBirthDate(), expectedEntity.getBirthDate(), "Should change birth date !");
        assertEquals(registrationDtoInput.getEmail(), expectedEntity.getEmail(), "Should change email !");
        assertEquals(registrationDtoInput.getPassword(), expectedEntity.getPassword(), "Should change password !");

        verify(userRepository).findByEmail(inputDto.getEmail());
        verify(passwordEncoder).encode(rawPassword);
        verify(userMapper).mapRegistrationDtoInputToEntity(registrationDtoInput);
        verify(userRepository).save(expectedEntity);
        verifyNoMoreInteractions(userRepository, passwordEncoder, userMapper);
    }

    @Test
    void login() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setEmail("ali@example.com");
        userEntity.setPassword("$2a$10$gEn9n7biN.TuvOvvXAy11OkVaAobZVYtDUZJZb4CKruLsMzfR3U8y");

        UserLoginDtoInput userLoginDtoInput = new UserLoginDtoInput();
        userLoginDtoInput.setEmail("ali@example.com");
        userLoginDtoInput.setPassword("string");

        Authentication authentication = mock(Authentication.class);
        String jwtToken = "jwt-token";

        when(userRepository.findByEmail(userLoginDtoInput.getEmail())).thenReturn(userEntity);
        when(passwordEncoder.matches(userLoginDtoInput.getPassword(), userEntity.getPassword())).thenReturn(true);
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtService.generateToken(userEntity)).thenReturn(jwtToken);

        AuthenticationDto result = userAuthService.login(userLoginDtoInput);

        assertEquals("jwt-token", result.getToken(), "JWT Token is invalid !");

        verify(userRepository).findByEmail(userLoginDtoInput.getEmail());
        verify(passwordEncoder).matches(userLoginDtoInput.getPassword(), userEntity.getPassword());
        verify(authManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(userEntity);
        verifyNoMoreInteractions(userRepository, passwordEncoder, authManager, jwtService);
    }

    @Test
    void getUser() {
        UserEntity mockUser = new UserEntity();
        mockUser.setId(1);
        mockUser.setEmail("test@example.com");

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        try (MockedStatic<SecurityContextHolder> mockedHolder = Mockito.mockStatic(SecurityContextHolder.class)) {
            mockedHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            UserEntity result = UserAuthService.getUser();

            assertNotNull(result, "User is null !");
            assertEquals("test@example.com", result.getEmail(), "User is not found !");

            verify(authentication).getPrincipal();
            verify(securityContext).getAuthentication();
        }
    }
}