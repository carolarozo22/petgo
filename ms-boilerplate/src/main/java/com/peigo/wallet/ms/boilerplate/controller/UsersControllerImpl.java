package com.peigo.wallet.ms.boilerplate.controller;

import com.peigo.wallet.ms.boilerplate.model.dto.UserDTO;
import com.peigo.wallet.ms.boilerplate.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/boilerplate")
public class UsersControllerImpl implements IUsersController{

    private final UserService userService;

    @Override
    @PostMapping(value = "/createUser", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.saveUser(userDTO));
    }

    @Override
    @GetMapping(value = "/getUsers", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getFindUser() {
        return ResponseEntity.ok(userService.getUsers());
    }
}
