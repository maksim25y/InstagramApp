package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserExistsException extends ApplicationRuntimeException {
    public UserExistsException(String email) {
        super(String.format("Пользователь с username : %s уже существует в системе", email), HttpStatus.NOT_FOUND, new Object[]{});
    }
}
