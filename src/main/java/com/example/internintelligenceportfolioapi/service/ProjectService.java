package com.example.internintelligenceportfolioapi.service;

import com.example.internintelligenceportfolioapi.dao.entity.ProjectEntity;
import com.example.internintelligenceportfolioapi.dao.entity.UserEntity;
import com.example.internintelligenceportfolioapi.dao.repository.ProjectRepository;
import com.example.internintelligenceportfolioapi.mapper.ProjectMapper;
import com.example.internintelligenceportfolioapi.model.input.ProjectDtoInput;
import com.example.internintelligenceportfolioapi.model.output.ProjectDtoOutput;
import com.example.internintelligenceportfolioapi.service.auth.UserAuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public List<ProjectDtoOutput> get(){
        log.info("Get Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        List<ProjectEntity> projectEntities = userEntity.getProjectEntities();
        return projectMapper.
                mapEntityToDtoOutputs(projectEntities);
    }

    @Transactional
    public void add(ProjectDtoInput projectDtoInput){
        log.info("Add Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        ProjectEntity projectEntity = projectMapper.
                mapProjectDtoInputToEntity(projectDtoInput);
        projectEntity.setUserEntity(userEntity);
        projectRepository.save(projectEntity);

        log.info("Add Ended ");
    }

    @Transactional
    public void updateName(Integer index, String newName){
        log.info("Update Name Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        ProjectEntity projectEntity = userEntity.
                getProjectEntities().get(index);
        projectEntity.setName(newName);
        projectRepository.save(projectEntity);

        log.info("Update Name Ended ");
    }

    @Transactional
    public void updateUrl(Integer index, String newUrl){
        log.info("Update Url Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        ProjectEntity projectEntity = userEntity.
                getProjectEntities().get(index);
        projectEntity.setUrl(newUrl);
        projectRepository.save(projectEntity);

        log.info("Update Url Ended ");
    }

    @Transactional
    public void updateAbout(Integer index, String newAbout){
        log.info("Update About Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        ProjectEntity projectEntity = userEntity.
                getProjectEntities().get(index);
        projectEntity.setAbout(newAbout);
        projectRepository.save(projectEntity);

        log.info("Update About Ended ");
    }

    @Transactional
    public void updateStartDate(Integer index, LocalDate newStartDate){
        log.info("Update Start Date Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        ProjectEntity projectEntity = userEntity.
                getProjectEntities().get(index);
        projectEntity.setStartDate(newStartDate);
        projectRepository.save(projectEntity);

        log.info("Update Start Date Ended ");
    }

    @Transactional
    public void updateEndDate(Integer index, LocalDate newEndDate){
        log.info("Update End Date Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        ProjectEntity projectEntity = userEntity.
                getProjectEntities().get(index);
        projectEntity.setEndDate(newEndDate);
        projectRepository.save(projectEntity);

        log.info("Update End Date Ended ");
    }

    @Transactional
    public void delete(Integer index){
        log.info("Delete Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        List<ProjectEntity> projectEntities = userEntity.
                getProjectEntities();
        ProjectEntity projectEntity = projectEntities.get(index);
        projectRepository.deleteById(projectEntity.getId());

        log.info("Delete Ended ");
    }
}
