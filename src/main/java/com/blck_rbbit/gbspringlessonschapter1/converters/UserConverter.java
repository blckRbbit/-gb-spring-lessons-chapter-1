package com.blck_rbbit.gbspringlessonschapter1.converters;

import com.blck_rbbit.gbspringlessonschapter1.dto.ProfileDto;
import com.blck_rbbit.gbspringlessonschapter1.dto.RegisterUserDto;
import com.blck_rbbit.gbspringlessonschapter1.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public ProfileDto entityToDTO(User user) {
        return new ProfileDto(user.getUsername());
    }
    public User DTOToEntity(RegisterUserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
