package com.nhut.identity_service.controllers;

import com.nhut.identity_service.payload.request.AuthenticationRequest;
import com.nhut.identity_service.payload.request.IntrospectRequest;
import com.nhut.identity_service.payload.response.APIResponse;
import com.nhut.identity_service.payload.response.AuthenticationResponse;
import com.nhut.identity_service.payload.response.IntrospectResponse;
import com.nhut.identity_service.services.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/log-in")
    public APIResponse<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return APIResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .result(this.authenticationService.authenticate(authenticationRequest))
                .build();
    }

    @PostMapping("/introspect")
    public APIResponse<?> introspect(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        return APIResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .result(this.authenticationService.introspect(introspectRequest))
                .build();
    }

}
