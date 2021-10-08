package com.peigo.wallet.ms.boilerplate.controller;

import com.peigo.wallet.ms.boilerplate.constants.SpringDoc;
import com.peigo.wallet.ms.boilerplate.dto.request.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = SpringDoc.CONTROLLER_USER_TAG_NAME, description = SpringDoc.CONTROLLER_USER_TAG_DESCRIPTION)
public interface IUsersController {
    @PostMapping(value = SpringDoc.CONTROLLER_USER_CREATE_USER_PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = SpringDoc.CONTROLLER_USER_CREATE_USER_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SpringDoc.CONTROLLER_USER_DESCRIPTION_200, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            }),
            @ApiResponse(responseCode = "404", description = SpringDoc.CONTROLLER_USER_DESCRIPTION_404, content = @Content)
    })
    ResponseEntity createUser(@Valid @RequestBody User user);

    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = SpringDoc.CONTROLLER_USER_FIND_USER_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SpringDoc.CONTROLLER_USER_DESCRIPTION_200, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            }),
            @ApiResponse(responseCode = "404", description = SpringDoc.CONTROLLER_USER_DESCRIPTION_404, content = @Content)
    })
    ResponseEntity getFindUser();
}
