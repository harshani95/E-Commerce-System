package com.ecom.user_service_api.service.impl;

import com.ecom.user_service_api.config.KeycloakSecurityUtil;
import com.ecom.user_service_api.dto.request.RequestUserLoginDto;
import com.ecom.user_service_api.dto.request.RequestUserSignupDto;
import com.ecom.user_service_api.entity.User;
import com.ecom.user_service_api.exception.DuplicateEntryException;
import com.ecom.user_service_api.exception.EntryNotFoundException;
import com.ecom.user_service_api.repo.UserRepo;
import com.ecom.user_service_api.service.UserService;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final KeycloakSecurityUtil securityUtil;
    private final UserRepo userRepo;

    @Value("${keycloak.config.realm}")
    private String realm;

    @Value("${keycloak.config.client-id}")
    private String clientId;

    @Value("${keycloak.config.secret}")
    private String secret;

    @Value("${spring.security.oauth2.resourceserver.jwt.token-uri}")
    private String apiUrl;

    @Override
    public void signup(RequestUserSignupDto userSignupDto) {

        String userId="";
        Keycloak keycloak=null;
        UserRepresentation representation= null;
        keycloak = securityUtil.getKeycloakInstance();
        representation =
                keycloak.realm(realm).users()
                        .search(userSignupDto.getEmail()).stream().findFirst().orElse(null);
        if(representation!=null){
            throw new DuplicateEntryException("Email is already exists");
        }

        UserRepresentation userRepresentation = convertUser(userSignupDto);
        Response response = keycloak.realm(realm).users().create(userRepresentation);
        if(response.getStatus()==Response.Status.CREATED.getStatusCode()){
            RoleRepresentation roleRepresentation = keycloak.realm(realm)
                    .roles().get("user").toRepresentation();
            userId =
                    response.getLocation().getPath().replaceAll(".*/([^/]+)$","$1");
            keycloak.realm(realm).users().get(userId).roles()
                    .realmLevel().add(Arrays.asList(roleRepresentation));
            User user = new User(
                    userId, userSignupDto.getEmail(), userSignupDto.getFirstName(), userSignupDto.getLastName()
            );
            userRepo.save(user);
        }
    }

    private UserRepresentation convertUser(RequestUserSignupDto userSignupDto) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userSignupDto.getEmail());
        userRepresentation.setFirstName(userSignupDto.getFirstName());
        userRepresentation.setLastName(userSignupDto.getLastName());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);

        List<CredentialRepresentation> credentialRepresentationList = new ArrayList<>();
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setValue(userSignupDto.getPassword());
        credentialRepresentationList.add(credentialRepresentation);
        userRepresentation.setCredentials(credentialRepresentationList);

        return userRepresentation;
    }

    @Override
    public Object login(RequestUserLoginDto userLoginDto) {

        try {
            Optional<User> selectedUser = userRepo.findByEmail(userLoginDto.getEmail());
            if (selectedUser.isEmpty()) {
                throw new EntryNotFoundException("Email not found");
            }

            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("client_id", clientId);
            requestBody.add("grant_type", OAuth2Constants.PASSWORD);
            requestBody.add("username", userLoginDto.getEmail());
            requestBody.add("client_secret", secret);
            requestBody.add("password", userLoginDto.getPassword());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Object> response =
                    restTemplate.postForEntity(apiUrl, requestBody, Object.class);
            return response.getBody();


        }catch (Exception e){
            throw new InternalServerErrorException(e);
        }
    }
}
