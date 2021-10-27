package com.peigo.wallet.ms.boilerplate.controller

import com.amazonaws.services.dynamodbv2.xspec.S
import com.fasterxml.jackson.databind.ObjectMapper
import com.peigo.wallet.ms.boilerplate.integrationtest.configuration.IntegrationTestConfiguration
import com.peigo.wallet.ms.boilerplate.model.dto.UserDTO
import com.peigo.wallet.ms.boilerplate.model.entity.UserEntity
import com.peigo.wallet.ms.boilerplate.repository.UserRepository
import com.peigo.wallet.ms.boilerplate.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.client.RestTemplate
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.core.waiters.WaiterResponse
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
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
    String TABLE_NAME = "Users"

    private MockMvc mockMvc

    @Autowired
    UsersControllerImpl usersController

    def setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(usersController)
                .build()
    }

    def "Test Container Connection"(){
        expect:
            dynamodbContainer.isRunning()
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