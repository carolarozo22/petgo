package com.peigo.wallet.ms.boilerplate.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.damianwajser.validator.annotation.enums.MatchEnum;
import com.github.damianwajser.validator.annotation.global.NotEmpty;
import com.peigo.wallet.ms.boilerplate.infraestructure.constants.CodeConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Credit Card DTO", description = "Contains the credit card information")
public class CreditCardDTO implements Serializable {

    @JsonProperty("number")
    @NotEmpty(message = "{validation.notEmpty.message}", businessCode = CodeConstant.VALIDATION_CODE_NOT_EMPTY)
    private long number;

    @JsonProperty("type")
    @NotEmpty(message = "{validation.notEmpty.message}", businessCode = CodeConstant.VALIDATION_CODE_NOT_EMPTY)
    @MatchEnum(message = "{validation.enumMatch.message}", businessCode = CodeConstant.VALIDATION_CODE_ENUM_NOT_MATCH, enumClass = TypeCreditCardEnum.class)
    private TypeCreditCardEnum typeCreditCardEnum;
}
