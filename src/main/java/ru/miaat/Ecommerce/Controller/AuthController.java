package ru.miaat.Ecommerce.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.miaat.Ecommerce.Dto.LoginRequest;
import ru.miaat.Ecommerce.Dto.Response;
import ru.miaat.Ecommerce.Dto.UserDto;
import ru.miaat.Ecommerce.Service.interf.UserService;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    private ResponseEntity<Response> registerUser(@RequestBody UserDto registerUser){
        log.info("Register endpoint called by {}", registerUser.getEmail());
        return ResponseEntity.ok(userService.registerUser(registerUser));
    }

    @PostMapping("/login")
    private ResponseEntity<Response> loginUser(@RequestBody LoginRequest loginUser){
        log.info("Login endpoint called by {}", loginUser.getEmail());
        ResponseEntity<Response> res = ResponseEntity.ok(userService.loginUser(loginUser));
        log.info("Response login created");
        return res;
    }
}
