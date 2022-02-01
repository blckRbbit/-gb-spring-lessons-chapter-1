package com.blck_rbbit.gbspringlessonschapter1.auth.dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
