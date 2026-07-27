package com.kinorify.kinorify_profile_svc.rest;

import com.kinorify.kinorify_profile_svc.config.JwtService;
import com.kinorify.kinorify_profile_svc.dto.request.CreateUserRequestDTO;
import com.kinorify.kinorify_profile_svc.dto.response.UserResponseDTO;
import com.kinorify.kinorify_profile_svc.entity.User;
import com.kinorify.kinorify_profile_svc.enums.AccountStatusType;
import com.kinorify.kinorify_profile_svc.repository.AccountStatusRepository;
import com.kinorify.kinorify_profile_svc.service.UserService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger log =
            LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final AccountStatusRepository accountStatusRepository;

    private final JwtService jwtService;



    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @RequestBody(required = false)
            CreateUserRequestDTO request,
            @AuthenticationPrincipal Jwt jwt) {


        String cognitoSub =
                jwtService.getCognitoSub(jwt);


        Optional<UserResponseDTO> existingUser =
                userService.getUserByCognitoSub(cognitoSub);


        if (existingUser.isPresent()) {

            log.warn(
                    "User {} already exists.",
                    cognitoSub);

            return ResponseEntity.ok(
                    existingUser.get());
        }


        User user =
                new User();


        user.setCognitoSub(
                cognitoSub);


        user.setEmail(
                jwtService.getEmail(jwt));


        user.setEmailVerified(
                jwtService.getEmailVerified(jwt));


        user.setAccountStatus(
                accountStatusRepository.findById(
                        AccountStatusType.ACTIVE.getId())
                        .orElseThrow());


        UserResponseDTO createdUser =
                userService.createUser(user);


        log.info(
                "Successful user creation with id {}.",
                createdUser.getUserId());


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUser);
    }



    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable UUID userId,
            @AuthenticationPrincipal Jwt jwt) {

        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @GetMapping("/cognito/{cognitoSub}")
    public ResponseEntity<UserResponseDTO> getUserByCognitoSub(
            @PathVariable String cognitoSub,
            @AuthenticationPrincipal Jwt jwt) {

        return userService.getUserByCognitoSub(cognitoSub)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(
            @PathVariable String email,
            @AuthenticationPrincipal Jwt jwt) {

        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(
            @AuthenticationPrincipal Jwt jwt) {

        return ResponseEntity.ok(
                userService.getAllUsers());
    }

}
