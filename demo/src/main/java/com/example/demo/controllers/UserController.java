package com.example.demo.controllers;

import com.example.demo.payload.response.UserDTO;
import com.example.demo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@Tag(name = "Пользователи", description = "API управления пользователями")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Получить текущего пользователя",
            description = "Возвращает информацию о текущем пользователе")
    @GetMapping("/")
    public UserDTO getCurrentUser(Principal principal) {
        return userService.getCurrentUser(principal);
    }

    @Operation(summary = "Получение пользователя по id",
            description = "Возвращает информацию о пользователе по его id")
    @GetMapping("/{userId}")
    public UserDTO getUserProfile(@PathVariable("userId") Long userId) {
        return userService.getUserById(userId);
    }

    @Operation(summary = "Обновление информации о пользователе",
            description = "Обновляет данные пользователя")
    @PutMapping
    public UserDTO updateUser(@Valid @RequestBody UserDTO userDTO, Principal principal) {
        return userService.updateUser(userDTO, principal);
    }
}
