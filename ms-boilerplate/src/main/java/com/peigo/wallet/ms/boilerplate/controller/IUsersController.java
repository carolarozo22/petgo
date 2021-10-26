package com.peigo.wallet.ms.boilerplate.controller;

import com.peigo.wallet.ms.boilerplate.infraestructure.constants.CodeConstant;
import com.peigo.wallet.ms.boilerplate.model.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "User Controller", description = "Controller for user registration and consultation")
public interface IUsersController {

    @Operation(summary = "User registration method")
    @ApiResponses(value = {
            @ApiResponse(responseCode = CodeConstant.RESPONSE_CODE_SUCCESS, description = CodeConstant.RESPONSE_TEXT_SUCCESS, content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDTO.class))
            }),
            @ApiResponse(responseCode = CodeConstant.RESPONSE_CODE_BAD_REQUEST, description = CodeConstant.RESPONSE_TEXT_BAD_REQUEST, content = @Content)
    })
    ResponseEntity<UserDTO> createUser(UserDTO userDTO);

    @Operation(summary = "User search method")
    @ApiResponses(value = {
            @ApiResponse(responseCode = CodeConstant.RESPONSE_CODE_SUCCESS, description = CodeConstant.RESPONSE_TEXT_SUCCESS, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDTO.class))
            }),
            @ApiResponse(responseCode = CodeConstant.RESPONSE_CODE_BAD_REQUEST, description = CodeConstant.RESPONSE_TEXT_BAD_REQUEST, content = @Content)
    })
    ResponseEntity<List<UserDTO>> getFindUser();

    ResponseEntity<String> deleteUser();

    ResponseEntity<Object> getHeaders();

    ResponseEntity<Object> getHeadersSnake();
}
