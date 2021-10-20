package com.peigo.wallet.ms.boilerplate.controller;

import com.peigo.wallet.ms.boilerplate.model.dto.UserDTO;
import com.peigo.wallet.ms.boilerplate.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/boilerplate")
public class UsersControllerImpl implements IUsersController{

    private final UserService userService;

    @Qualifier("snake_template")
    private final RestTemplate restTemplate;

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

    @GetMapping(value = "/getHeaders", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object getObjectHeaders(){
        return restTemplate.getForObject("https://httpbin.org/get", Object.class);
    }
}
