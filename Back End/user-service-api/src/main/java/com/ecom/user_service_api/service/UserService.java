package com.ecom.user_service_api.service;

import com.ecom.user_service_api.dto.request.RequestUserLoginDto;
import com.ecom.user_service_api.dto.request.RequestUserSignupDto;

public interface UserService {
    public void signup(RequestUserSignupDto userSignupDto);
    public Object login(RequestUserLoginDto userLoginDto);
}
