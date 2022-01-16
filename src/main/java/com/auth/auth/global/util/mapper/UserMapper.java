package com.auth.auth.global.util.mapper;

import com.auth.auth.domain.user.domain.User;
import com.auth.auth.domain.user.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends GenericMapper<UserDto, User> {

}
