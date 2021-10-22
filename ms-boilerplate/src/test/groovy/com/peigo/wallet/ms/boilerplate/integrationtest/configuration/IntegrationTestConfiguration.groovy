package com.peigo.wallet.ms.boilerplate.integrationtest.configuration

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.annotation.DirtiesContext
import org.testcontainers.containers.GenericContainer
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
abstract class IntegrationTestConfiguration extends Specification {
    private static final int DYNAMODB_PORT = 8000;
    private static final String AWS_ACCESS_KEY_ID = "peigo";
    private static final String AWS_SECRET_KEY_ID = "globant2021";

    public static DynamoDbAsyncClient asyncClient;

    public static GenericContainer dynamodbContainer = new GenericContainer<>("amazon/dynamodb-local:latest")
            .withExposedPorts(DYNAMODB_PORT)

    static {

        dynamodbContainer.start()

        System.setProperty("test.server.sql-host", "${dynamodbContainer.host}")
        System.setProperty("test.server.sql-port", "${dynamodbContainer.firstMappedPort}")

        asyncClient = DynamoDbAsyncClient
                .builder()
                .region(Region.US_EAST_1)
                .endpointOverride(URI.create(buildEndpointUrl()))
                .credentialsProvider(
                        ()-> AwsBasicCredentials.create(AWS_ACCESS_KEY_ID, AWS_SECRET_KEY_ID)
                )
                .build()
    }


    static String buildEndpointUrl() {
        return "http://" + dynamodbContainer.host + ":"  + dynamodbContainer.firstMappedPort
    }

}