package com.example.internintelligenceportfolioapi.service;

import com.example.internintelligenceportfolioapi.dao.entity.ProjectEntity;
import com.example.internintelligenceportfolioapi.dao.entity.UserEntity;
import com.example.internintelligenceportfolioapi.dao.repository.ProjectRepository;
import com.example.internintelligenceportfolioapi.mapper.ProjectMapper;
import com.example.internintelligenceportfolioapi.model.input.ProjectDtoInput;
import com.example.internintelligenceportfolioapi.model.output.ProjectDtoOutput;
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
class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void get() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(1);
        projectEntity.setName("Reserone");
        projectEntity.setUrl("reserone.az");
        projectEntity.setAbout("Barber Reservation Project");
        projectEntity.setStartDate(LocalDate.of(2024, 7, 1));
        projectEntity.setEndDate(LocalDate.of(2024, 12, 1));
        List<ProjectEntity> projectEntities = List.of(projectEntity);
        userEntity.setProjectEntities(projectEntities);

        ProjectDtoOutput projectDtoOutput = new ProjectDtoOutput();
        projectDtoOutput.setName("Reserone");
        projectDtoOutput.setUrl("reserone.az");
        projectDtoOutput.setAbout("Barber Reservation Project");
        projectDtoOutput.setStartDate(LocalDate.of(2024, 7, 1));
        projectDtoOutput.setEndDate(LocalDate.of(2024, 12, 1));
        List<ProjectDtoOutput> projectDtoOutputs = List.of(projectDtoOutput);

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);
            when(projectMapper.mapEntityToDtoOutputs(projectEntities)).thenReturn(projectDtoOutputs);

            List<ProjectDtoOutput> result = projectService.get();

            assertEquals("Reserone", projectEntities.get(0).getName(), "Should change name !");
            assertEquals(result.get(0).getUrl(), projectEntities.get(0).getUrl(), "Should change url !");
            assertEquals(result.get(0).getAbout(), projectEntities.get(0).getAbout(), "Should change about !");
            assertEquals(result.get(0).getStartDate(), projectEntities.get(0).getStartDate(), "Should change start date !");
            assertEquals(result.get(0).getEndDate(), projectEntities.get(0).getEndDate(), "Should change end date !");

            mockedStatic.verify(UserAuthService::getUser);
            verify(projectMapper).mapEntityToDtoOutputs(projectEntities);
        }
    }

    @Test
    @Transactional
    void add() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);

        ProjectDtoInput projectDtoInput = new ProjectDtoInput();
        projectDtoInput.setName("Reserone");
        projectDtoInput.setUrl("reserone.az");
        projectDtoInput.setAbout("Barber Reservation Project");
        projectDtoInput.setStartDate(LocalDate.of(2024, 7, 1));
        projectDtoInput.setEndDate(LocalDate.of(2024, 12, 1));

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(1);
        projectEntity.setName("Reserone");
        projectEntity.setUrl("reserone.az");
        projectEntity.setAbout("Barber Reservation Project");
        projectEntity.setStartDate(LocalDate.of(2024, 7, 1));
        projectEntity.setEndDate(LocalDate.of(2024, 12, 1));

        List<ProjectEntity> projectEntities = List.of(projectEntity);
        userEntity.setProjectEntities(projectEntities);

        try (MockedStatic<UserAuthService> mockedStatic = Mockito.mockStatic(UserAuthService.class)) {
            mockedStatic.when(UserAuthService::getUser).thenReturn(userEntity);
            when(projectMapper.mapProjectDtoInputToEntity(projectDtoInput)).thenReturn(projectEntity);

            projectService.add(projectDtoInput);
            ProjectEntity actualEntity = userEntity.getProjectEntities().get(0);

            assertEquals(projectDtoInput.getName(), actualEntity.getName(), "Should change name !");
            assertEquals(projectDtoInput.getUrl(), actualEntity.getUrl(), "Should change url !");
            assertEquals(projectDtoInput.getAbout(), actualEntity.getAbout(), "Should change about !");
            assertEquals(projectDtoInput.getStartDate(), actualEntity.getStartDate(), "Should change start date !");
            assertEquals(projectDtoInput.getEndDate(), actualEntity.getEndDate(), "Should change end date !");

            mockedStatic.verify(UserAuthService::getUser);
            verify(projectMapper).mapProjectDtoInputToEntity(projectDtoInput);
            verify(projectRepository).save(projectEntity);
        }
    }
}