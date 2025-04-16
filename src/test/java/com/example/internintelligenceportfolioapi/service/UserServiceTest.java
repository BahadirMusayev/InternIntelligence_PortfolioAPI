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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

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
            assertEquals(userEntity.getName(), result.getName(), "Name not mapped correctly !");
            assertEquals(userEntity.getSurname(), result.getSurname(), "Surname not mapped correctly !");
            assertEquals(userEntity.getBirthDate(), result.getBirthDate(), "Birth Date is not mapped correctly !");
            assertEquals(userEntity.getEmail(), result.getEmail(), "Email is not mapped correctly !");
            assertEquals(userEntity.getSkills(), result.getSkills(), "Skills are not mapped correctly !");

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

            assertNotNull(userEntity.getName(), "Name is not null !");
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

            assertNotNull(userEntity.getSurname(), "Surname is not null !");
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

            assertNotNull(userEntity.getBirthDate(), "Birth Date is not null !");
            assertEquals(LocalDate.of(2006, 4, 14), userEntity.getBirthDate(), "Should change birth date !");
            mockedStatic.verify(UserAuthService::getUser);
            verify(userRepository).save(userEntity);
        }
    }

    @Test
    @Transactional
    void deleteBirthDate() {
        UserEntity userEntity = new UserEntity();
        userEntity.setBirthDate(LocalDate.of(1990, 1, 1));

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);

            userService.deleteBirthDate();
            assertNull(userEntity.getBirthDate(), "Birth Date should be null !");

            mockedStatic.verify(UserAuthService::getUser);
            verify(userRepository).save(userEntity);
        }
    }

    @Test
    @Transactional
    void updateEmail() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("bahadurmusayev15@gmail.com");

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);

            String newEmail = "john@gmail.com";
            userService.updateEmail(newEmail);

            assertNotNull(userEntity.getEmail(), "Email is not null !");
            assertEquals("john@gmail.com", userEntity.getEmail(), "Should change email !");
            mockedStatic.verify(UserAuthService::getUser);
            verify(userRepository).save(userEntity);
        }
    }

    @Test
    @Transactional
    void updatePassword() {
        UserEntity userEntity = new UserEntity();
        String originalEncodedPassword = "$2a$10$.XxkQhLZ5Hj3yxdyo9veZein5GfjAe4lIOYcMsR7oXZx/wHBFYcMa";
        userEntity.setPassword(originalEncodedPassword);

        String currentPassword = "string";
        String newPassword = "new_secret";
        String newEncodedPassword = "$2a$10$pSxG4n64jT6ydFy1JKOJTO5SQS96mo27AR9I.k3FB0Dc71CZjGHc2";

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);
            when(passwordEncoder.matches(currentPassword, originalEncodedPassword)).thenReturn(true);
            when(passwordEncoder.encode(newPassword)).thenReturn(newEncodedPassword);

            userService.updatePassword(currentPassword, newPassword);

            assertEquals(newEncodedPassword, userEntity.getPassword(), "Password must be encoded and updated!");
            mockedStatic.verify(UserAuthService::getUser);
            verify(passwordEncoder).encode(newPassword);
            verify(passwordEncoder).matches(currentPassword, originalEncodedPassword);
            verify(userRepository).save(userEntity);
        }
    }

    @Test
    @Transactional
    void addSkill() {
        UserEntity userEntity = new UserEntity();
        Set<String> skills = new HashSet<>();
        skills.add("Java");
        skills.add("Spring");
        userEntity.setSkills(skills);

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);

            String newSkill = "Microservices";
            userService.addSkill(newSkill);

            assertNotNull(userEntity.getSkills(), "Skills is not null !");
            assertEquals(Set.of("Java", "Spring", "Microservices"), userEntity.getSkills(), "Skill isn't add !");
            mockedStatic.verify(UserAuthService::getUser);
            verify(userRepository).save(userEntity);
        }
    }

    @Test
    @Transactional
    void deleteSkill() {
        UserEntity userEntity = new UserEntity();
        Set<String> skills = new HashSet<>();
        skills.add("Java");
        skills.add("Spring");
        userEntity.setSkills(skills);

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);

            String skill = "Java";
            userService.deleteSkill(skill);

            assertEquals(Set.of("Spring"), userEntity.getSkills(), "Skill isn't delete !");
            mockedStatic.verify(UserAuthService::getUser);
            verify(userRepository).save(userEntity);
        }
    }

    @Test
    @Transactional
    void delete() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("Ali");
        userEntity.setSurname("Ali");
        userEntity.setBirthDate(LocalDate.of(1990, 1, 1));
        userEntity.setEmail("ali@example.com");
        userEntity.setPassword("hidden089");
        userEntity.setSkills(Set.of("Java", "Spring"));

        try(MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)){
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);

            userService.delete();

            mockedStatic.verify(UserAuthService::getUser);
            verify(userRepository).deleteById(userEntity.getId());
        }
    }
}