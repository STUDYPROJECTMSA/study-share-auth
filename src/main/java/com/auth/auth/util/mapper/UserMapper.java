package com.auth.auth.util.mapper;

import com.auth.auth.user.domain.User;
import com.auth.auth.user.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends GenericMapper<UserDto, User> {

}
