package com.peigo.wallet.ms.boilerplate.model.mapper;

import com.peigo.wallet.ms.boilerplate.model.dto.UserDTO;
import com.peigo.wallet.ms.boilerplate.model.entity.UserEntity;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "name", source = "name"),
        @Mapping(target = "email", source = "email"),
        @Mapping(target = "age", source = "age"),
        @Mapping(target = "creditCardEntityList", source = "creditCardDTOList")
    })
    UserEntity toEntity(UserDTO userDTO);

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "name", source = "name"),
        @Mapping(target = "email", source = "email"),
        @Mapping(target = "age", source = "age"),
            @Mapping(target = "creditCardDTOList", source = "creditCardEntityList")
    })
    UserDTO toDto(UserEntity userEntity);
}
