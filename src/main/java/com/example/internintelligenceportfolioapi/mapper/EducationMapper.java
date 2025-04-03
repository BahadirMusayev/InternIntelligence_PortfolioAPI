package com.example.internintelligenceportfolioapi.mapper;

import com.example.internintelligenceportfolioapi.dao.entity.EducationEntity;
import com.example.internintelligenceportfolioapi.model.input.EducationDtoInput;
import com.example.internintelligenceportfolioapi.model.output.EducationDtoOutput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EducationMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "speciality", source = "speciality")
    @Mapping(target = "degree", source = "degree")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    EducationEntity mapEducationDtoInputToEntity(EducationDtoInput educationDtoInput);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "speciality", source = "speciality")
    @Mapping(target = "degree", source = "degree")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    EducationDtoOutput mapEntityToDtoOutput(EducationEntity educationEntity);

    List<EducationDtoOutput> mapEntityToDtoOutputs(List<EducationEntity> educationEntities);

}
