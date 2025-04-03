package com.example.internintelligenceportfolioapi.mapper;

import com.example.internintelligenceportfolioapi.dao.entity.UserEntity;
import com.example.internintelligenceportfolioapi.model.input.UserRegistrationDtoInput;
import com.example.internintelligenceportfolioapi.model.output.UserDtoOutput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    UserEntity mapRegistrationDtoInputToEntity(UserRegistrationDtoInput userRegistrationDto);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "skills", source = "skills")
    UserDtoOutput mapEntityToDtoOutput(UserEntity userEntity);
}
