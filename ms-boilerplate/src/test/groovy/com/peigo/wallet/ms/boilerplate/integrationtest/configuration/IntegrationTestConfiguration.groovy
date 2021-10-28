package com.peigo.wallet.ms.boilerplate.integrationtest.configuration


import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.testcontainers.containers.GenericContainer
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.core.waiters.WaiterResponse
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.*
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbAsyncWaiter
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter
import spock.lang.Specification

import java.util.concurrent.CompletableFuture

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
abstract class IntegrationTestConfiguration extends Specification {
    private static final int DYNAMODB_PORT = 8000
    public static final String AWS_ACCESS_KEY_ID = "peigo"
    public static final String AWS_SECRET_KEY_ID = "Globant2021"

    public static DynamoDbAsyncClient asyncClient


    public static GenericContainer dynamodbContainer = new GenericContainer<>("amazon/dynamodb-local:latest")
            .withCommand("-jar DynamoDBLocal.jar -inMemory -sharedDb")
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

        createTableUsers()
    }


    static String buildEndpointUrl() {
        return "http://" + dynamodbContainer.host + ":"  + dynamodbContainer.firstMappedPort
    }

    static String createTableAsync(String tableName) {
        String tableNameResult = ""
        CreateTableRequest createTableRequest = CreateTableRequest
                .builder()
                .attributeDefinitions(
                        AttributeDefinition
                                .builder()
                                .attributeName("id")
                                .attributeType(ScalarAttributeType.S)
                                .build()
                )
                .keySchema(
                        KeySchemaElement
                                .builder()
                                .attributeName("id")
                                .keyType(KeyType.HASH)
                                .build()
                )
                .provisionedThroughput(
                        ProvisionedThroughput
                                .builder()
                                .readCapacityUnits(10)
                                .writeCapacityUnits(10)
                                .build()
                )
                .tableName(tableName)
                .build() as CreateTableRequest

        DynamoDbAsyncWaiter asyncWaiter = asyncClient.waiter()
        CompletableFuture<CreateTableResponse> completableFuture = asyncClient.createTable(createTableRequest)

        completableFuture.whenComplete((table, error)->{
            try {
                if ( table != null ) {
                    println("Table " + table)
                    tableNameResult = table.tableDescription().tableName()
                    DescribeTableRequest describeTableRequest = DescribeTableRequest.builder().tableName(table.tableDescription().tableName()).build() as DescribeTableRequest
                    CompletableFuture<WaiterResponse<DescribeTableResponse>> completableFutureResponse = asyncWaiter.waitUntilTableExists(describeTableRequest)
                    completableFutureResponse.whenComplete((table2, error2)->{
                        String dynID = table2.matched().response().get().table().tableArn()
                        println("dynID:::" + dynID)
                        String tableDesciption = table2.matched().response().get().table().toString()
                        println("Table Description::::" + tableDesciption)

                    })
                    completableFutureResponse.join()
                } else {
                    println("Error::" + error)
                }
            } finally {
                println("Cliente Cerrado")
                asyncClient.close()
            }
        })
        completableFuture.join()

        return tableNameResult
    }

    static String createTable(DynamoDbClient ddb, String tableName, String key) {

        DynamoDbWaiter dbWaiter = ddb.waiter()
        CreateTableRequest request = CreateTableRequest
                .builder()
                .attributeDefinitions(
                        AttributeDefinition
                                .builder()
                                .attributeName(key)
                                .attributeType(ScalarAttributeType.S)
                                .build()
                )
                .keySchema(
                        KeySchemaElement
                                .builder()
                                .attributeName(key)
                                .keyType(KeyType.HASH)
                                .build()
                )
                .provisionedThroughput(
                        ProvisionedThroughput
                                .builder()
                                .readCapacityUnits(10)
                                .writeCapacityUnits(10)
                                .build()
                )
                .tableName(tableName)
                .build() as CreateTableRequest

        String newTable
        try {
            CreateTableResponse response = ddb.createTable(request)
            DescribeTableRequest tableRequest = DescribeTableRequest.builder()
                    .tableName(tableName)
                    .build() as DescribeTableRequest

            // Wait until the Amazon DynamoDB table is created
            WaiterResponse<DescribeTableResponse> waiterResponse =  dbWaiter.waitUntilTableExists(tableRequest)
            waiterResponse.matched().response().ifPresent(System.out::println)

            newTable = response.tableDescription().tableName()
            System.out.println("New table is " + newTable)
            return newTable

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage())
            System.exit(1)
        }
        return ""
    }


    static void createTableUsers() {
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(URI.create(buildEndpointUrl()))
                .credentialsProvider(
                        ()-> AwsBasicCredentials.create(AWS_ACCESS_KEY_ID, AWS_SECRET_KEY_ID)
                )
                .build()

        createTable(ddb, "Users", "id")

        ddb.close()
    }
}