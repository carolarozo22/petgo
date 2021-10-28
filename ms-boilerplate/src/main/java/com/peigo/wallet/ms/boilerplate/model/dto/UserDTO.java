package com.peigo.wallet.ms.boilerplate.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.damianwajser.validator.annotation.global.Email;
import com.github.damianwajser.validator.annotation.global.Max;
import com.github.damianwajser.validator.annotation.global.Min;
import com.github.damianwajser.validator.annotation.global.NotEmpty;
import com.peigo.wallet.ms.boilerplate.infraestructure.constants.CodeConstant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "User DTO", description = "Contains all the user's data transfer information")
public class UserDTO implements Serializable{

    @JsonProperty("id")
    @Schema(description = "ID Users", example = "fd1b1477-be6b-4386-9cca-a8ac4791b51c")
    private String id;

    @JsonProperty("name")
    @NotEmpty(message = "{validation.notEmpty.message}", businessCode = CodeConstant.VALIDATION_CODE_NOT_EMPTY)
    @Schema(description = "User name not empty", example = "Erick")
    private String name;

    @JsonProperty("documentNumber")
    @NotEmpty(message = "{validation.notEmpty.message}", businessCode = CodeConstant.VALIDATION_CODE_NOT_EMPTY)
    @Schema(description = "Document Number not empty", example = "1234567890")
    private String documentNumber;

    @JsonProperty("mobile")
    @NotEmpty(message = "{validation.notEmpty.message}", businessCode = CodeConstant.VALIDATION_CODE_NOT_EMPTY)
    @Schema(description = "Mobile not empty", example = "30030030000")
    private long mobile;

    @JsonProperty("password")
    @NotEmpty(message = "{validation.notEmpty.message}", businessCode = CodeConstant.VALIDATION_CODE_NOT_EMPTY)
    @Schema(description = "User name not empty", example = "Abc123$$$.")
    private String password;

    @JsonProperty("email")
    @Email(message = "{validation.email.message}", businessCode = CodeConstant.VALIDATION_CODE_NOT_EMAIL_FORMAT)
    @Schema(description = "Validate the user email", example = "peigo@globant.com")
    private String email;

    @JsonProperty("age")
    @Min(min = 18, message = "{validation.min.message}", businessCode = CodeConstant.VALIDATION_CODE_MIN_VALUE)
    @Max(max = 70, message = "{validation.max.message}", businessCode = CodeConstant.VALIDATION_CODE_MAX_VALUE)
    @Schema(description = "Validate the age between 18 and 70", example = "24")
    private int age;

    @JsonProperty("creditCards")
    @Schema(description = "Credit cards list", required = true)
    private List<@Valid CreditCardDTO> creditCardDTOList;
}

