package com.gen.eChannel.verification.services;

import com.gen.eChannel.verification.dto.LoginDto;
import com.gen.eChannel.verification.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    LoginDto login(LoginDto loginDto);
}
