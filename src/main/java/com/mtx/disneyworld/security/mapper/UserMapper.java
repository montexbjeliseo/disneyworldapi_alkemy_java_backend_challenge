package com.mtx.disneyworld.security.mapper;

import com.mtx.disneyworld.security.dto.UserRegisterDto;
import com.mtx.disneyworld.security.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRegisterDto dto);
}
