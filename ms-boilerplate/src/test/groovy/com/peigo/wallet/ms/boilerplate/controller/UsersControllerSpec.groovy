package com.peigo.wallet.ms.boilerplate.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.peigo.wallet.ms.boilerplate.integrationtest.configuration.IntegrationTestConfiguration
import com.peigo.wallet.ms.boilerplate.model.dto.UserDTO
import com.peigo.wallet.ms.boilerplate.service.UserService
import org.springframework.http.MediaType
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import software.amazon.awssdk.core.waiters.WaiterResponse
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest
import software.amazon.awssdk.services.dynamodb.model.CreateTableResponse
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest
import software.amazon.awssdk.services.dynamodb.model.DescribeTableResponse
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement
import software.amazon.awssdk.services.dynamodb.model.KeyType
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbAsyncWaiter

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

import java.util.concurrent.CompletableFuture

class UsersControllerSpec extends IntegrationTestConfiguration {
    String TABLE_NAME = "User"

    private MockMvc mockMvc


    UsersControllerImpl usersController

    UserService userService

    def setup(){
        userService = Mock(UserService.class)
        usersController = new UsersControllerImpl(userService)

        ReflectionTestUtils.setField(usersController, "userService", userService)

        mockMvc = MockMvcBuilders.standaloneSetup(usersController)
                .build()
    }

    def "Test Connection"(){
        expect:
        dynamodbContainer.isRunning()
    }
    def "Create table"() {
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
                .tableName(TABLE_NAME)
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

        expect:
        TABLE_NAME == tableNameResult
    }

    def "Create User"() {
        given:
        def userDTO = UserDTO.builder()
                .age(20)
                .email("correo.prueba@gmail.com")
                .name("Erick Jimenez")
                .build()

        expect:
        mockMvc.perform(post("/api/boilerplate/createUser")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())

    }

    def "Get Users"() {
        expect:"Get Users"
        mockMvc.perform(get("/api/boilerplate/getUsers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
    }

}