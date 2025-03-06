package com.example.demo.controllers;

import com.example.demo.exceptions.ApplicationRuntimeException;
import com.example.demo.exceptions.CustomError;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(ApplicationRuntimeException.class)
    public ResponseEntity<CustomError> handleApplicationException(ApplicationRuntimeException exception, Locale locale) {
        return createProblemDetailResponse(
                exception.getHttpStatus(),
                exception.getMessage(),
                exception.getArgs(),
                locale
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomError> handleUnauthorizedException(BadCredentialsException e, Locale locale) {
        return createProblemDetailResponse(
                HttpStatus.UNAUTHORIZED,
                "user.unauthorized",
                new Object[] {e.getMessage()},
                locale
        );
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<CustomError> handleBindException(BindException exception, Locale locale) {
        var problemDetail = createProblemDetail(
                HttpStatus.BAD_REQUEST,
                "errors.400.title",
                new Object[0],
                locale
        );
        List<String> errors = exception.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage).collect(Collectors.toList());
        problemDetail.setDetail(String.join("; ", errors));
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> handleValidationException(MethodArgumentNotValidException exception, Locale locale) {
        var problemDetail = createProblemDetail(
                HttpStatus.BAD_REQUEST,
                "errors.400.title",
                new Object[0],
                locale
        );
        List<String> errors = exception.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage).collect(Collectors.toList());
        problemDetail.setDetail(String.join("; ", errors));
        return ResponseEntity.badRequest().body(problemDetail);
    }

    private ResponseEntity<CustomError> createProblemDetailResponse(
            HttpStatus status, String messageKey, Object[] args, Locale locale
    ) {
        var problemDetail = createProblemDetail(status, messageKey, args, locale);
        return ResponseEntity.status(status).body(problemDetail);
    }

    private CustomError createProblemDetail(HttpStatus status, String messageKey, Object[] args, Locale locale) {
        String message = Objects.requireNonNull(
                messageSource.getMessage(messageKey, args, messageKey, locale)
        );
        return new CustomError(status.value(), messageKey, message);
    }
}
