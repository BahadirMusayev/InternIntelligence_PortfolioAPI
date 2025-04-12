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
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("Ali");
        userEntity.setSurname("Ali");
        userEntity.setBirthDate(LocalDate.of(1990, 1, 1));
        userEntity.setEmail("ali@example.com");
        userEntity.setPassword("hidden089");
        userEntity.setSkills(Set.of("Java", "Spring"));

        UserDtoOutput userDtoOutput = new UserDtoOutput();
        userDtoOutput.setName("Ali");
        userDtoOutput.setSurname("Ali");
        userDtoOutput.setBirthDate(LocalDate.of(1990, 1, 1));
        userDtoOutput.setEmail("ali@example.com");
        userDtoOutput.setSkills(Set.of("Java", "Spring"));

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);
            when(userMapper.mapEntityToDtoOutput(userEntity)).thenReturn(userDtoOutput);

            UserDtoOutput result = userService.get();

            assertNotNull(result, "Result is not null !");
            assertEquals(expectedDto.getName(), result.getName(), "Name not mapped correctly !");
            assertEquals(expectedDto.getSurname(), result.getSurname(), "Surname not mapped correctly !");
            assertEquals(expectedDto.getBirthDate(), result.getBirthDate(), "Birth Date is not mapped correctly !");
            assertEquals(expectedDto.getEmail(), result.getEmail(), "Email is not mapped correctly !");
            assertEquals(expectedDto.getSkills(), result.getSkills(), "Skills are not mapped correctly !");

            mockedStatic.verify(UserAuthService::getUser);
            verify(userMapper).mapEntityToDtoOutput(userEntity);
        }
    }

    @Test
    @Transactional
    void updateName() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("Bahadur");

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);

            String newName = "John";
            userService.updateName(newName);

            assertEquals("John", userEntity.getName(), "Should change name !");
            mockedStatic.verify(UserAuthService::getUser);
            verify(userRepository).save(userEntity);
        }
    }

    @Test
    @Transactional
    void updateSurname() {
        UserEntity userEntity = new UserEntity();
        userEntity.setSurname("Musayev");

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);

            String newSurname = "Aleksandr";
            userService.updateSurname(newSurname);

            assertEquals("Aleksandr", userEntity.getSurname(), "Should change surname !");
            mockedStatic.verify(UserAuthService::getUser);
            verify(userRepository).save(userEntity);
        }
    }

    @Test
    @Transactional
    void updateBirthDate() {
        UserEntity userEntity = new UserEntity();
        userEntity.setBirthDate(LocalDate.of(2006, 4, 14));

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);

            LocalDate newBirthDate = LocalDate.of(2006, 4, 14);
            userService.updateBirthDate(newBirthDate);

            assertEquals(LocalDate.of(2006,4,14), userEntity.getBirthDate(), "Should change birth date !");
            mockedStatic.verify(UserAuthService::getUser);
            verify(userRepository).save(userEntity);
        }
    }

    @Test
    @Transactional
    void deleteBirthDate() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("Ali");
        userEntity.setSurname("Ali");
        userEntity.setBirthDate(LocalDate.of(1990, 1, 1));
        userEntity.setEmail("ali@example.com");
        userEntity.setSkills(Set.of("Java", "Spring"));

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);

            userService.deleteBirthDate();
            UserDtoOutput result = userService.get();

            assertNull(result, "");
        }
    }
}