package com.example.internintelligenceportfolioapi.mapper;

import com.example.internintelligenceportfolioapi.dao.entity.ProjectEntity;
import com.example.internintelligenceportfolioapi.model.input.ProjectDtoInput;
import com.example.internintelligenceportfolioapi.model.output.ProjectDtoOutput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "about", source = "about")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    ProjectEntity mapProjectDtoInputToEntity(ProjectDtoInput projectDtoInput);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "about", source = "about")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    ProjectDtoOutput mapEntityToDtoOutput(ProjectEntity projectEntity);

    List<ProjectDtoOutput> mapEntityToDtoOutputs(List<ProjectEntity> projectEntities);
}
