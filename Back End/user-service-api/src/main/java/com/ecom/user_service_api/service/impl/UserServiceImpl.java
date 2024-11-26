package com.ecom.user_service_api.service.impl;

import com.ecom.user_service_api.dto.request.RequestUserLoginDto;
import com.ecom.user_service_api.dto.request.RequestUserSignupDto;
import com.ecom.user_service_api.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public void signup(RequestUserSignupDto userSignupDto) {

    }

    @Override
    public Object login(RequestUserLoginDto userLoginDto) {
        return null;
    }
}
