package it.pkg.code.demo.login.model;

import lombok.Data;

@Data
public class PasswordModify {
    private Long id;
    private String newPassword;
    private String oldPassword;
}
