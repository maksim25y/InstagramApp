package com.example.demo.controllers;

import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.response.JWTTokenSuccessResponse;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.security.JWTTokenProvider;
import com.example.demo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
//позволяет запросам из других доменов (origin)
// обращаться к вашему веб-приложению
@CrossOrigin
@RequestMapping("/api/auth")
//имеют доступ все пользователи
@Tag(name = "Аутентификация", description = "API управления аутентификацией")
@PreAuthorize("permitAll()")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    @Operation(summary = "Аутентификация пользователя",
            description = "Аутентифицирует пользователя с предоставленными учетными данными")
    @PostMapping("/signin")
    public JWTTokenSuccessResponse authenticationUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        return new JWTTokenSuccessResponse(true, jwt);
    }

    @Operation(summary = "Регистрация нового пользователя",
            description = "Регистрирует нового пользователя с предоставленной информацией")
    @PostMapping("/signup")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        userService.createUser(signupRequest);
        return ResponseEntity.ok().build();
    }

}
