package com.blck_rbbit.gbspringlessonschapter1.controllers;

import com.blck_rbbit.gbspringlessonschapter1.converters.UserConverter;
import com.blck_rbbit.gbspringlessonschapter1.dto.RegisterUserDto;
import com.blck_rbbit.gbspringlessonschapter1.entities.User;
import com.blck_rbbit.gbspringlessonschapter1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    private final UserConverter userConverter;
    
    @PostMapping("/registration")
    public ResponseEntity<?> registerNewUser(@RequestBody RegisterUserDto userDto) {
        User user = userConverter.DTOToEntity(userDto);
        userService.createNewUser(user);
        return ResponseEntity.ok().build();
    }
}
