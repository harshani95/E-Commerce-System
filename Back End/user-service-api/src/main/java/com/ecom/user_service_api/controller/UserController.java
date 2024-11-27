package com.ecom.user_service_api.controller;

import com.ecom.user_service_api.dto.request.RequestUserLoginDto;
import com.ecom.user_service_api.dto.request.RequestUserSignupDto;
import com.ecom.user_service_api.service.UserService;
import com.ecom.user_service_api.util.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-service/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<StandardResponse> createUser(@RequestBody RequestUserSignupDto userSignupDto){
        userService.signup(userSignupDto);
        return new ResponseEntity<>(
                new StandardResponse(201,null,"User created"),
                HttpStatus.CREATED
        );
    }
    @PostMapping("/login")
    public ResponseEntity<StandardResponse> createUser(@RequestBody RequestUserLoginDto userLoginDto){
        return new ResponseEntity<>(
                new StandardResponse(200,userService.login(userLoginDto),"User Logged"),
                HttpStatus.OK
        );
    }

}
