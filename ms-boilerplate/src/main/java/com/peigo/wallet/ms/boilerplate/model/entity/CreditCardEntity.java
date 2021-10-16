package com.peigo.wallet.ms.boilerplate.model.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Data;

@Data
@DynamoDBDocument
public class CreditCardEntity {

    private long number;
    private String typeCreditCardEnum;
}
