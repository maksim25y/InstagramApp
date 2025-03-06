package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PostNotFoundException extends ApplicationRuntimeException {
    public PostNotFoundException(Long id) {

        super(String.format("Пост с id %s не найден", id), HttpStatus.NOT_FOUND, new Object[]{});
    }
}
