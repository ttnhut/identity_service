package com.nhut.identity_service.services;

import com.nhut.identity_service.payload.request.AuthenticationRequest;
import com.nhut.identity_service.payload.request.IntrospectRequest;
import com.nhut.identity_service.payload.response.AuthenticationResponse;
import com.nhut.identity_service.payload.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {

    /**
     * Check authentication
     * @param authenticationRequest authenticationRequest
     * @return true if authenticated else false
     */
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    /**
     * Verify token
     * @param introspectRequest introspectRequest
     * @return IntrospectResponse
     */
    IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException;
}
