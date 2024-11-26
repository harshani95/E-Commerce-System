package com.ecom.user_service_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestUserSignupDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
