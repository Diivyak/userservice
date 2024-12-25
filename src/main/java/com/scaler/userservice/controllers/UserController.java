package com.scaler.userservice.controllers;


import com.scaler.userservice.dtos.SignUpRequestDto;
import com.scaler.userservice.models.User;
import com.scaler.userservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    public User login() {
        return null;
    }

    @PostMapping("/")
    public User signUp(SignUpRequestDto request){
        String email = request.getEmail();
        String password = request.getPassword();
        String name = request.getName();

        return userService.signUp(name, email, password);
    }

    public ResponseEntity<Void> logout() {
        return null;
    }

}
