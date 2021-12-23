package com.blck_rbbit.gbspringlessonschapter1.controllers;

import com.blck_rbbit.gbspringlessonschapter1.converters.UserConverter;
import com.blck_rbbit.gbspringlessonschapter1.dto.ProfileDto;
import com.blck_rbbit.gbspringlessonschapter1.entities.User;
import com.blck_rbbit.gbspringlessonschapter1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    private final UserConverter userConverter;
    
    @PostMapping("/registration")
    ProfileDto save(@RequestBody String name, String password, String email) {
        User user = new User(name, password, email);
        userService.saveNewUser(name, password, email);
        return userConverter.entityToDTO(user);
    }
}
