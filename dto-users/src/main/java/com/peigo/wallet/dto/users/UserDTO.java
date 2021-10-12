package com.peigo.wallet.dto.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.peigo.wallet.dto.users.constants.DTOConstant;
import com.peigo.wallet.dto.users.constants.SpringDocConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Builder
@Schema(name = SpringDocConstant.DTO_USER_SCHEMA_NAME, description = SpringDocConstant.DTO_USER_SCHEMA_DESCRIPTION)
public class UserDTO implements Serializable {

    @NotEmpty(message = SpringDocConstant.DTO_USER_MESSAGE_NAME)
    @Schema(description = SpringDocConstant.DTO_USER_SCHEMA_NAME_DESCRIPTION, required = true, example = SpringDocConstant.DTO_USER_SCHEMA_NAME_EXAMPLE)
    @JsonProperty(DTOConstant.DTO_USER_NAME)
    private String name;

    @Email(message = SpringDocConstant.DTO_USER_MESSAGE_EMAIL)
    @JsonProperty(DTOConstant.DTO_USER_EMAIL)
    private String email;

    @Min(value = 18, message = SpringDocConstant.DTO_USER_MESSAGE_AGE)
    @JsonProperty(DTOConstant.DTO_USER_AGE)
    private int age;
}
