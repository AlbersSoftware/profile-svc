package com.kinorify.kinorify_profile_svc.rest;

import com.kinorify.kinorify_profile_svc.dto.request.CreateUserRequestDTO;
import com.kinorify.kinorify_profile_svc.dto.response.UserResponseDTO;
import com.kinorify.kinorify_profile_svc.entity.User;
import com.kinorify.kinorify_profile_svc.enums.AccountStatusType;
import com.kinorify.kinorify_profile_svc.repository.AccountStatusRepository;
import com.kinorify.kinorify_profile_svc.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final AccountStatusRepository accountStatusRepository;


    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @RequestBody CreateUserRequestDTO request) {

        // TODO:
        // Once Cognito JWT authentication is configured,
        // remove CreateUserRequestDTO and instead extract:
        //
        //  sub            -> cognitoSub
        //  email          -> email
        //  email_verified -> emailVerified
        //
        // directly from the authenticated JWT.

        User user = new User();

        user.setCognitoSub(request.getCognitoSub());

        user.setEmail(request.getEmail());

        user.setEmailVerified(request.isEmailVerified());

        user.setAccountStatus(
                accountStatusRepository.findById(
                        AccountStatusType.ACTIVE.getId())
                        .orElseThrow());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(user));
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable UUID userId) {

        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/cognito/{cognitoSub}")
    public ResponseEntity<UserResponseDTO> getUserByCognitoSub(
            @PathVariable String cognitoSub) {

        return userService.getUserByCognitoSub(cognitoSub)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(
            @PathVariable String email) {

        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {

        return ResponseEntity.ok(
                userService.getAllUsers());
    }

}
