package com.example.internintelligenceportfolioapi.mapper;

import com.example.internintelligenceportfolioapi.dao.entity.EducationEntity;
import com.example.internintelligenceportfolioapi.dao.entity.ExperienceEntity;
import com.example.internintelligenceportfolioapi.model.input.EducationDtoInput;
import com.example.internintelligenceportfolioapi.model.input.ExperienceDtoInput;
import com.example.internintelligenceportfolioapi.model.output.EducationDtoOutput;
import com.example.internintelligenceportfolioapi.model.output.ExperienceDtoOutput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExperienceMapper {
    @Mapping(target = "workPlace", source = "workPlace")
    @Mapping(target = "jobTitle", source = "jobTitle")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    ExperienceEntity mapExperienceDtoInputToEntity(ExperienceDtoInput experienceDtoInput);

    @Mapping(target = "workPlace", source = "workPlace")
    @Mapping(target = "jobTitle", source = "jobTitle")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    ExperienceDtoOutput mapEntityToDtoOutput(ExperienceEntity experienceEntity);

    List<ExperienceDtoOutput> mapEntityToDtoOutputs(List<ExperienceEntity> experienceEntities);
}
