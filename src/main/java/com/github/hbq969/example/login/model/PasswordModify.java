package com.github.hbq969.example.login.model;

import lombok.Data;

@Data
public class PasswordModify {
    private Long id;
    private String newPassword;
    private String oldPassword;
}
