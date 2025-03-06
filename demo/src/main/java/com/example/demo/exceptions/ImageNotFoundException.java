package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImageNotFoundException extends ApplicationRuntimeException {
    public ImageNotFoundException(Long id) {
        super(String.format("Изображение с id %s не найдено", id), HttpStatus.NOT_FOUND, new Object[]{});
    }
}
