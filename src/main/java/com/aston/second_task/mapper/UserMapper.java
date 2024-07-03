package com.aston.second_task.mapper;

import com.aston.second_task.dto.incoming.UserDTOInc;
import com.aston.second_task.dto.outgoing.UserDTOOut;
import com.aston.second_task.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
        @Mapping(source = "firstName", target = "firstName")
        @Mapping(source = "lastName", target = "lastName")
        @Mapping(source = "email", target = "email")
        @Mapping(source = "phone", target = "phone")
        @Mapping(source = "password", target = "password")
        @Mapping(source = "address", target = "address")
        @Mapping(source = "role", target = "role")
        AppUser userDTOIncToUser(UserDTOInc userDTOInc);
        @Mapping(source = "firstName", target = "firstName")
        @Mapping(source = "lastName", target = "lastName")
        @Mapping(source = "email", target = "email")
    UserDTOOut userToUserDTOOut(AppUser appUser);

}
