package com.example.internintelligenceportfolioapi.service;

import com.example.internintelligenceportfolioapi.dao.entity.ExperienceEntity;
import com.example.internintelligenceportfolioapi.dao.entity.UserEntity;
import com.example.internintelligenceportfolioapi.dao.repository.ExperienceRepository;
import com.example.internintelligenceportfolioapi.mapper.ExperienceMapper;
import com.example.internintelligenceportfolioapi.model.input.ExperienceDtoInput;
import com.example.internintelligenceportfolioapi.model.output.ExperienceDtoOutput;
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
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;

    public List<ExperienceDtoOutput> get(){
        log.info("Get Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        List<ExperienceEntity> experienceEntities = userEntity.getExperienceEntities();
        return experienceMapper.
                mapEntityToDtoOutputs(experienceEntities);
    }

    @Transactional
    public void add(ExperienceDtoInput experienceDtoInput){
        log.info("Add Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        ExperienceEntity experienceEntity = experienceMapper.
                mapExperienceDtoInputToEntity(experienceDtoInput);
        experienceEntity.setUserEntity(userEntity);
        experienceRepository.save(experienceEntity);

        log.info("Add Ended ");
    }

    @Transactional
    public void updateWorkPlace(Integer index, String newWorkPlace){
        log.info("Update Work Place Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        ExperienceEntity experienceEntity = userEntity.
                getExperienceEntities().get(index);
        experienceEntity.setWorkPlace(newWorkPlace);
        experienceRepository.save(experienceEntity);

        log.info("Update Work Place Ended ");
    }

    @Transactional
    public void updateJobTitle(Integer index, String newJobTitle){
        log.info("Update Job Title Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        ExperienceEntity experienceEntity = userEntity.
                getExperienceEntities().get(index);
        experienceEntity.setJobTitle(newJobTitle);
        experienceRepository.save(experienceEntity);

        log.info("Update Job Title Ended ");
    }



    @Transactional
    public void updateStartDate(Integer index, LocalDate newStartDate){
        log.info("Update Start Date Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        ExperienceEntity experienceEntity = userEntity.
                getExperienceEntities().get(index);
        experienceEntity.setStartDate(newStartDate);
        experienceRepository.save(experienceEntity);

        log.info("Update Start Date Ended ");
    }

    @Transactional
    public void updateEndDate(Integer index, LocalDate newEndDate){
        log.info("Update End Date Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        ExperienceEntity experienceEntity = userEntity.
                getExperienceEntities().get(index);
        experienceEntity.setEndDate(newEndDate);
        experienceRepository.save(experienceEntity);

        log.info("Update End Date Ended ");
    }

    @Transactional
    public void delete(Integer index){
        log.info("Delete Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        List<ExperienceEntity> experienceEntities = userEntity.
                getExperienceEntities();
        ExperienceEntity experienceEntity = experienceEntities.get(index);
        experienceRepository.deleteById(experienceEntity.getId());

        log.info("Delete Ended ");
    }
}
