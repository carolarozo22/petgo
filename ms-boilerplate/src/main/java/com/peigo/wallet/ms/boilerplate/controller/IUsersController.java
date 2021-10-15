package com.peigo.wallet.ms.boilerplate.controller;

import com.peigo.wallet.ms.boilerplate.constants.ControllerConstants;
import com.peigo.wallet.ms.boilerplate.constants.SpringDocConstant;
import com.peigo.wallet.ms.boilerplate.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Tag(name = SpringDocConstant.CONTROLLER_USER_TAG_NAME, description = SpringDocConstant.CONTROLLER_USER_TAG_DESCRIPTION)
public interface IUsersController {
    @PostMapping(value = ControllerConstants.CONTROLLER_USER_CREATE_USER_PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = SpringDocConstant.CONTROLLER_USER_CREATE_USER_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = ControllerConstants.CONTROLLER_RESPONSE_CODE_OK, description = SpringDocConstant.CONTROLLER_USER_DESCRIPTION_200, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            }),
            @ApiResponse(responseCode = ControllerConstants.CONTROLLER_RESPONSE_CODE_NOT_FOUND, description = SpringDocConstant.CONTROLLER_USER_DESCRIPTION_404, content = @Content)
    })
    ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO);

    @GetMapping(value = ControllerConstants.CONTROLLER_USER_GET_USER_PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = SpringDocConstant.CONTROLLER_USER_FIND_USER_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = ControllerConstants.CONTROLLER_RESPONSE_CODE_OK, description = SpringDocConstant.CONTROLLER_USER_DESCRIPTION_200, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            }),
            @ApiResponse(responseCode = ControllerConstants.CONTROLLER_RESPONSE_CODE_NOT_FOUND, description = SpringDocConstant.CONTROLLER_USER_DESCRIPTION_404, content = @Content)
    })
    ResponseEntity<List<UserDTO>> getFindUser();
}
