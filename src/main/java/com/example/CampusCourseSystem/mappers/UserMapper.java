package com.example.CampusCourseSystem.mappers;

import com.example.CampusCourseSystem.dto.Account.UserRegisterModel;
import com.example.CampusCourseSystem.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRegisterModel toDto(User user);

    User toEntity(UserRegisterModel dto);

}
