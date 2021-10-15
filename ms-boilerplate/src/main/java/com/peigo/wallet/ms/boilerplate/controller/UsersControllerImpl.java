package com.peigo.wallet.ms.boilerplate.controller;

import com.peigo.wallet.dto.users.UserDTO;
import com.peigo.wallet.ms.boilerplate.service.IUserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UsersControllerImpl implements IUsersController{

    @Autowired
    private IUserService userServiceImpl;

    @Override
    public ResponseEntity createUser(UserDTO userDTO) {
        return ResponseEntity.ok(userServiceImpl.saveUser(userDTO));
    }

    @Override
    public ResponseEntity getFindUser() {
        return ResponseEntity.ok(userServiceImpl.getUsers());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(@NotNull MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String name = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(name, errorMessage);
        });
        return errors;
    }
}
