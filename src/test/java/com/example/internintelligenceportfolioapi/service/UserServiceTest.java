package com.example.internintelligenceportfolioapi.service;

import com.example.internintelligenceportfolioapi.dao.entity.UserEntity;
import com.example.internintelligenceportfolioapi.dao.repository.UserRepository;
import com.example.internintelligenceportfolioapi.mapper.UserMapper;
import com.example.internintelligenceportfolioapi.model.output.UserDtoOutput;
import com.example.internintelligenceportfolioapi.service.auth.UserAuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void get() {
        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setId(1);
        mockUserEntity.setName("Ali");
        mockUserEntity.setSurname("Ali");
        mockUserEntity.setBirthDate(LocalDate.of(1990, 1, 1));
        mockUserEntity.setEmail("ali@example.com");
        mockUserEntity.setSkills(Set.of("Java", "Spring"));

        UserDtoOutput expectedDto = UserDtoOutput.builder()
                .name("Ali")
                .surname("Ali")
                .birthDate(LocalDate.of(1990, 1, 1))
                .email("ali@example.com")
                .skills(Set.of("Java", "Spring"))
                .build();

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(mockUserEntity);
            when(userMapper.mapEntityToDtoOutput(mockUserEntity)).thenReturn(expectedDto);

            UserDtoOutput result = userService.get();

            assertNotNull(result, "Result is not null !");
            assertEquals(expectedDto.getName(), result.getName(), "Name not mapped correctly !");
            assertEquals(expectedDto.getSurname(), result.getSurname(), "Surname not mapped correctly !");
            assertEquals(expectedDto.getBirthDate(), result.getBirthDate(), "Birth Date is not mapped correctly !");
            assertEquals(expectedDto.getEmail(), result.getEmail(), "Email is not mapped correctly !");
            assertEquals(expectedDto.getSkills(), result.getSkills(), "Skills are not mapped correctly !");

            mockedStatic.verify(UserAuthService::getUser);
            verify(userMapper).mapEntityToDtoOutput(mockUserEntity);
            verifyNoMoreInteractions(userMapper);
        }
    }

    @Test
    @Transactional
    void updateName() {
        UserEntity mockUser = new UserEntity();
        mockUser.setName("Bahadur");

        String newName = "John";

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(mockUser);

            userService.updateName(newName);

            assertEquals(newName, mockUser.getName(), "Should change name !");
            verify(userRepository).save(mockUser);
        }
    }

    @Test
    @Transactional
    void updateSurname() {
        UserEntity mockUser = new UserEntity();
        mockUser.setSurname("Musayev");

        String newSurname = "Aleksandr";

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(mockUser);

            userService.updateSurname(newSurname);

            assertEquals(newSurname, mockUser.getSurname(), "Should change surname !");
            verify(userRepository).save(mockUser);
        }
    }
}