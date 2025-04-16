package com.example.internintelligenceportfolioapi.service;

import com.example.internintelligenceportfolioapi.dao.entity.ExperienceEntity;
import com.example.internintelligenceportfolioapi.dao.entity.UserEntity;
import com.example.internintelligenceportfolioapi.dao.repository.ExperienceRepository;
import com.example.internintelligenceportfolioapi.mapper.ExperienceMapper;
import com.example.internintelligenceportfolioapi.model.input.ExperienceDtoInput;
import com.example.internintelligenceportfolioapi.model.output.ExperienceDtoOutput;
import com.example.internintelligenceportfolioapi.service.auth.UserAuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ExperienceServiceTest {
    @Mock
    private ExperienceRepository experienceRepository;

    @Mock
    private ExperienceMapper experienceMapper;

    @InjectMocks
    private ExperienceService experienceService;

    @Test
    void get() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);

        ExperienceEntity experienceEntity = new ExperienceEntity();
        experienceEntity.setId(1);
        experienceEntity.setWorkPlace("Kapital Bank");
        experienceEntity.setJobTitle("Java Backend Development");
        experienceEntity.setStartDate(LocalDate.of(2024, 7, 1));
        experienceEntity.setEndDate(LocalDate.of(2024, 12, 1));

        List<ExperienceEntity> experienceEntities = List.of(experienceEntity);
        userEntity.setExperienceEntities(experienceEntities);

        ExperienceDtoOutput experienceDtoOutput = new ExperienceDtoOutput();
        experienceDtoOutput.setWorkPlace("Kapital Bank");
        experienceDtoOutput.setJobTitle("Java Backend Development");
        experienceDtoOutput.setStartDate(LocalDate.of(2024, 7, 1));
        experienceDtoOutput.setEndDate(LocalDate.of(2024, 12, 1));
        List<ExperienceDtoOutput> experienceDtoOutputs = List.of(experienceDtoOutput);

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);
            when(experienceMapper.mapEntityToDtoOutputs(experienceEntities)).thenReturn(experienceDtoOutputs);

            List<ExperienceDtoOutput> result = experienceService.get();

            assertEquals(result.get(0).getWorkPlace(), experienceEntities.get(0).getWorkPlace(), "Should change work place !");
            assertEquals(result.get(0).getJobTitle(), experienceEntities.get(0).getJobTitle(), "Should change job title !");
            assertEquals(result.get(0).getStartDate(), experienceEntities.get(0).getStartDate(), "Should change start date !");
            assertEquals(result.get(0).getEndDate(), experienceEntities.get(0).getEndDate(), "Should change end date !");

            mockedStatic.verify(UserAuthService::getUser);
            verify(experienceMapper).mapEntityToDtoOutputs(experienceEntities);
        }
    }

    @Test
    @Transactional
    void add() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);

        ExperienceDtoInput experienceDtoInput = new ExperienceDtoInput();
        experienceDtoInput.setWorkPlace("Compar");
        experienceDtoInput.setJobTitle("Java Backend Development");
        experienceDtoInput.setStartDate(LocalDate.of(2024, 7, 1));
        experienceDtoInput.setEndDate(LocalDate.of(2024, 12, 1));

        ExperienceEntity experienceEntity = new ExperienceEntity();
        experienceEntity.setId(1);
        experienceEntity.setWorkPlace("Compar");
        experienceEntity.setJobTitle("Java Backend Development");
        experienceEntity.setStartDate(LocalDate.of(2024, 7, 1));
        experienceEntity.setEndDate(LocalDate.of(2024, 12, 1));

        List<ExperienceEntity> experienceEntities = List.of(experienceEntity);
        userEntity.setExperienceEntities(experienceEntities);

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);
            when(experienceMapper.mapExperienceDtoInputToEntity(experienceDtoInput)).thenReturn(experienceEntity);

            experienceService.add(experienceDtoInput);
            ExperienceEntity actualEntity = userEntity.getExperienceEntities().get(0);

            assertEquals(experienceEntity.getWorkPlace(), actualEntity.getWorkPlace(), "Should change work place !");
            assertEquals(experienceEntity.getJobTitle(), actualEntity.getJobTitle(), "Should change job title !");
            assertEquals(experienceEntity.getStartDate(), actualEntity.getStartDate(), "Should change start date !");
            assertEquals(experienceEntity.getEndDate(), actualEntity.getEndDate(), "Should change end date !");

            mockedStatic.verify(UserAuthService::getUser);
            verify(experienceMapper).mapExperienceDtoInputToEntity(experienceDtoInput);
            verify(experienceRepository).save(experienceEntity);
        }
    }

    @Test
    @Transactional
    void updateWorkPlace() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);

        ExperienceEntity experienceEntity = new ExperienceEntity();
        experienceEntity.setId(1);
        experienceEntity.setWorkPlace("Kapital Bank");

        List<ExperienceEntity> experienceEntities = List.of(experienceEntity);
        userEntity.setExperienceEntities(experienceEntities);

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);

            String newWorkPlace = "Microsoft";
            experienceService.updateWorkPlace(0, newWorkPlace);

            assertNotNull(experienceEntity.getWorkPlace(), "Work Place is null !");
            assertEquals(newWorkPlace, experienceEntity.getWorkPlace(), "Should change work place !");

            mockedStatic.verify(UserAuthService::getUser);
            verify(experienceRepository).save(experienceEntity);
        }
    }

    @Test
    @Transactional
    void updateJobTitle() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);

        ExperienceEntity experienceEntity = new ExperienceEntity();
        experienceEntity.setId(1);
        experienceEntity.setJobTitle("Java");

        List<ExperienceEntity> experienceEntities = List.of(experienceEntity);
        userEntity.setExperienceEntities(experienceEntities);

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);

            String newJobTitle = "C#";
            experienceService.updateJobTitle(0, newJobTitle);

            assertNotNull(experienceEntity.getJobTitle(), "Job Title is null !");
            assertEquals(newJobTitle, experienceEntity.getJobTitle(), "Should change job title !");

            mockedStatic.verify(UserAuthService::getUser);
            verify(experienceRepository).save(experienceEntity);
        }
    }

    @Test
    @Transactional
    void updateStartDate() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);

        ExperienceEntity experienceEntity = new ExperienceEntity();
        experienceEntity.setId(1);
        experienceEntity.setStartDate(LocalDate.of(1990, 1, 1));

        List<ExperienceEntity> experienceEntities = List.of(experienceEntity);
        userEntity.setExperienceEntities(experienceEntities);

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);

            LocalDate newStartDate = LocalDate.of(1990, 1, 1);
            experienceService.updateStartDate(0, newStartDate);

            assertNotNull(experienceEntity.getStartDate(), "Start Date is null !");
            assertEquals(newStartDate, experienceEntity.getStartDate(), "Should change start date !");

            mockedStatic.verify(UserAuthService::getUser);
            verify(experienceRepository).save(experienceEntity);
        }
    }

    @Test
    @Transactional
    void updateEndDate() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);

        ExperienceEntity experienceEntity = new ExperienceEntity();
        experienceEntity.setId(1);
        experienceEntity.setEndDate(LocalDate.of(1990, 1, 1));

        List<ExperienceEntity> experienceEntities = List.of(experienceEntity);
        userEntity.setExperienceEntities(experienceEntities);

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);

            LocalDate newEndDate = LocalDate.of(1990, 1, 1);
            experienceService.updateEndDate(0, newEndDate);

            assertNotNull(experienceEntity.getEndDate(), "End Date is null !");
            assertEquals(newEndDate, experienceEntity.getEndDate(), "Should change end date !");

            mockedStatic.verify(UserAuthService::getUser);
            verify(experienceRepository).save(experienceEntity);
        }
    }

    @Test
    @Transactional
    void delete() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);

        ExperienceEntity experienceEntity = new ExperienceEntity();
        experienceEntity.setId(1);
        experienceEntity.setWorkPlace("KKB");
        experienceEntity.setJobTitle("Java Backend Development");
        experienceEntity.setStartDate(LocalDate.of(2024, 7, 1));
        experienceEntity.setEndDate(LocalDate.of(2024, 12, 1));

        List<ExperienceEntity> experienceEntities = List.of(experienceEntity);
        userEntity.setExperienceEntities(experienceEntities);

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);

            experienceService.delete(0);

            mockedStatic.verify(UserAuthService::getUser);
            verify(experienceRepository).deleteById(1);
        }
    }
}