package com.example.internintelligenceportfolioapi.service;

import com.example.internintelligenceportfolioapi.dao.entity.EducationEntity;
import com.example.internintelligenceportfolioapi.dao.entity.ExperienceEntity;
import com.example.internintelligenceportfolioapi.dao.entity.UserEntity;
import com.example.internintelligenceportfolioapi.dao.repository.ExperienceRepository;
import com.example.internintelligenceportfolioapi.mapper.ExperienceMapper;
import com.example.internintelligenceportfolioapi.model.output.EducationDtoOutput;
import com.example.internintelligenceportfolioapi.model.output.ExperienceDtoOutput;
import com.example.internintelligenceportfolioapi.service.auth.UserAuthService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}