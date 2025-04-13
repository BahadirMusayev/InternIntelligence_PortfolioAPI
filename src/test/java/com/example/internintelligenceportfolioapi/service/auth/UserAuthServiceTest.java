package com.example.internintelligenceportfolioapi.service.auth;

import com.example.internintelligenceportfolioapi.dao.entity.UserEntity;
import com.example.internintelligenceportfolioapi.dao.repository.UserRepository;
import com.example.internintelligenceportfolioapi.mapper.UserMapper;
import com.example.internintelligenceportfolioapi.model.input.UserRegistrationDtoInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @InjectMocks
    private UserAuthService userAuthService;

    @Test
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

        when(userRepository.findByEmail(inputDto.getEmail())).thenReturn(userEntity);
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
}