package com.blck_rbbit.gbspringlessonschapter1.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {
    private String name;
    private String email;
    private String password;
}
