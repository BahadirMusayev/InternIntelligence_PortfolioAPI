package com.example.internintelligenceportfolioapi.service;

import com.example.internintelligenceportfolioapi.dao.entity.EducationEntity;
import com.example.internintelligenceportfolioapi.dao.entity.UserEntity;
import com.example.internintelligenceportfolioapi.dao.repository.EducationRepository;
import com.example.internintelligenceportfolioapi.mapper.EducationMapper;
import com.example.internintelligenceportfolioapi.model.input.EducationDtoInput;
import com.example.internintelligenceportfolioapi.model.output.EducationDtoOutput;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EducationServiceTest {
    @Mock
    private EducationRepository educationRepository;

    @Mock
    private EducationMapper educationMapper;

    @InjectMocks
    private EducationService educationService;

    @Test
    void get() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);

        EducationEntity educationEntity = new EducationEntity();
        educationEntity.setId(1);
        educationEntity.setName("Compar");
        educationEntity.setSpeciality("Java Backend Development");
        educationEntity.setDegree("Advanced");
        educationEntity.setStartDate(LocalDate.of(2024, 7, 1));
        educationEntity.setEndDate(LocalDate.of(2024, 12, 1));
        List<EducationEntity> educationEntities = List.of(educationEntity);
        userEntity.setEducationEntities(educationEntities);

        EducationDtoOutput educationDtoOutput = new EducationDtoOutput();
        educationDtoOutput.setName("Compar");
        educationDtoOutput.setSpeciality("Java Backend Development");
        educationDtoOutput.setDegree("Advanced");
        educationDtoOutput.setStartDate(LocalDate.of(2024, 7, 1));
        educationDtoOutput.setEndDate(LocalDate.of(2024, 12, 1));
        List<EducationDtoOutput> educationDtoOutputs = List.of(educationDtoOutput);

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);
            when(educationMapper.mapEntityToDtoOutputs(educationEntities)).thenReturn(educationDtoOutputs);

            List<EducationDtoOutput> result = educationService.get();

            assertEquals(result.get(0).getName(), educationEntities.get(0).getName(), "Should change name !");
            assertEquals(result.get(0).getSpeciality(), educationEntities.get(0).getSpeciality(), "Should change speciality !");
            assertEquals(result.get(0).getDegree(), educationEntities.get(0).getDegree(), "Should change degree !");
            assertEquals(result.get(0).getStartDate(), educationEntities.get(0).getStartDate(), "Should change start date !");
            assertEquals(result.get(0).getEndDate(), educationEntities.get(0).getEndDate(), "Should change end date !");

            mockedStatic.verify(UserAuthService::getUser);
            verify(educationMapper).mapEntityToDtoOutputs(educationEntities);
        }
    }

    @Test
    @Transactional
    void add() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);

        EducationDtoInput educationDtoInput = new EducationDtoInput();
        educationDtoInput.setName("Compar");
        educationDtoInput.setSpeciality("Java Backend Development");
        educationDtoInput.setDegree("Advanced");
        educationDtoInput.setStartDate(LocalDate.of(2024, 7, 1));
        educationDtoInput.setEndDate(LocalDate.of(2024, 12, 1));

        EducationEntity educationEntity = new EducationEntity();
        educationEntity.setId(1);
        educationEntity.setName("Compar");
        educationEntity.setSpeciality("Java Backend Development");
        educationEntity.setDegree("Advanced");
        educationEntity.setStartDate(LocalDate.of(2024, 7, 1));
        educationEntity.setEndDate(LocalDate.of(2024, 12, 1));

        List<EducationEntity> educationEntities = List.of(educationEntity);
        userEntity.setEducationEntities(educationEntities);

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);
            when(educationMapper.mapEducationDtoInputToEntity(educationDtoInput)).thenReturn(educationEntity);

            educationService.add(educationDtoInput);
            EducationEntity actualEntity = userEntity.getEducationEntities().get(0);

            assertEquals(educationDtoInput.getName(), actualEntity.getName(), "Should change name !");
            assertEquals(educationDtoInput.getSpeciality(), actualEntity.getSpeciality(), "Should change speciality !");
            assertEquals(educationDtoInput.getDegree(), actualEntity.getDegree(), "Should change degree !");
            assertEquals(educationDtoInput.getStartDate(), actualEntity.getStartDate(), "Should change start date !");
            assertEquals(educationDtoInput.getEndDate(), actualEntity.getEndDate(), "Should change end date !");

            mockedStatic.verify(UserAuthService::getUser);
            verify(educationMapper).mapEducationDtoInputToEntity(educationDtoInput);
            verify(educationRepository).save(educationEntity);
        }
    }
}