package com.example.internintelligenceportfolioapi.service;

import com.example.internintelligenceportfolioapi.dao.entity.EducationEntity;
import com.example.internintelligenceportfolioapi.dao.entity.UserEntity;
import com.example.internintelligenceportfolioapi.dao.repository.EducationRepository;
import com.example.internintelligenceportfolioapi.mapper.EducationMapper;
import com.example.internintelligenceportfolioapi.model.input.EducationDtoInput;
import com.example.internintelligenceportfolioapi.model.output.EducationDtoOutput;
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
public class EducationService {

    private final EducationRepository educationRepository;
    private final EducationMapper educationMapper;

    public List<EducationDtoOutput> get(){
        log.info("Get Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        List<EducationEntity> educationEntities = userEntity.getEducationEntities();
        return educationMapper.
                mapEntityToDtoOutputs(educationEntities);
    }

    @Transactional
    public void add(EducationDtoInput educationDtoInput){
        log.info("Add Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        EducationEntity educationEntity = educationMapper.
                mapEducationDtoInputToEntity(educationDtoInput);
        educationEntity.setUserEntity(userEntity);
        educationRepository.save(educationEntity);

        log.info("Add Ended ");
    }

    @Transactional
    public void updateName(Integer index, String newName){
        log.info("Update Name Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        EducationEntity educationEntity = userEntity.
                getEducationEntities().get(index);
        educationEntity.setName(newName);
        educationRepository.save(educationEntity);

        log.info("Update Name Ended ");
    }

    @Transactional
    public void updateSpeciality(Integer index, String newSpeciality){
        log.info("Update Speciality Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        EducationEntity educationEntity = userEntity.
                getEducationEntities().get(index);
        educationEntity.setSpeciality(newSpeciality);
        educationRepository.save(educationEntity);

        log.info("Update Speciality Ended ");
    }

    @Transactional
    public void updateDegree(Integer index, String newDegree){
        log.info("Update Degree Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        EducationEntity educationEntity = userEntity.
                getEducationEntities().get(index);
        educationEntity.setDegree(newDegree);
        educationRepository.save(educationEntity);

        log.info("Update Degree Ended ");
    }

    @Transactional
    public void updateStartDate(Integer index, LocalDate newStartDate){
        log.info("Update Start Date Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        EducationEntity educationEntity = userEntity.
                getEducationEntities().get(index);
        educationEntity.setStartDate(newStartDate);
        educationRepository.save(educationEntity);

        log.info("Update Start Date Ended ");
    }

    @Transactional
    public void updateEndDate(Integer index, LocalDate newEndDate){
        log.info("Update End Date Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        EducationEntity educationEntity = userEntity.
                getEducationEntities().get(index);
        educationEntity.setEndDate(newEndDate);
        educationRepository.save(educationEntity);

        log.info("Update End Date Ended ");
    }

    @Transactional
    public void delete(Integer index){
        log.info("Delete Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        List<EducationEntity> educationEntities = userEntity.
                getEducationEntities();
        EducationEntity educationEntity = educationEntities.get(index);
        educationRepository.deleteById(educationEntity.getId());

        log.info("Delete Ended ");
    }
}
