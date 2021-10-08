package com.peigo.wallet.ms.boilerplate.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.peigo.wallet.ms.boilerplate.constants.SpringDoc;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@Schema(name = SpringDoc.DTO_USER_SCHEMA_NAME, description = SpringDoc.DTO_USER_SCHEMA_DESCRIPTION)
public class User {

    @NotEmpty(message = SpringDoc.DTO_USER_MESSAGE_NAME)
    @Schema(description = SpringDoc.DTO_USER_SCHEMA_NAME_DESCRIPTION, required = true, example = SpringDoc.DTO_USER_SCHEMA_NAME_EXAMPLE)
    @JsonProperty(SpringDoc.DTO_USER_NAME)
    private String name;

    @Email(message = SpringDoc.DTO_USER_MESSAGE_EMAIL)
    @JsonProperty(SpringDoc.DTO_USER_EMAIL)
    private String email;

    @Min(value = 18, message = SpringDoc.DTO_USER_MESSAGE_AGE)
    @JsonProperty(SpringDoc.DTO_USER_AGE)
    private int age;
}
