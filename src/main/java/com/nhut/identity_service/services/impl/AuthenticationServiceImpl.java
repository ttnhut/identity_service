package com.nhut.identity_service.services.impl;

import com.nhut.identity_service.exception.AppException;
import com.nhut.identity_service.exception.ErrorCode;
import com.nhut.identity_service.payload.request.AuthenticationRequest;
import com.nhut.identity_service.payload.request.IntrospectRequest;
import com.nhut.identity_service.payload.response.AuthenticationResponse;
import com.nhut.identity_service.payload.response.IntrospectResponse;
import com.nhut.identity_service.repositories.UserRepository;
import com.nhut.identity_service.services.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        var user = this.userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(() ->
                new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());

        var token = this.generateToken(user.getUsername());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return AuthenticationResponse
                .builder()
                .token(token)
                .authenticated(authenticated)
                .build();
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException {
        var token = introspectRequest.getToken();

        JWSVerifier jwsVerifier = new MACVerifier(secretKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        signedJWT.verify(jwsVerifier);

        return IntrospectResponse
                .builder()
                .valid(signedJWT.verify(jwsVerifier) && expiryTime.after(new Date()))
                .build();
    }

    private String generateToken(String username) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("localhost:3306")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("custom", "custom")
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }
}
