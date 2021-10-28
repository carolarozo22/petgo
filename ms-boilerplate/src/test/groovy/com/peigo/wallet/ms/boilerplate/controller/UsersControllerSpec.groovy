package com.peigo.wallet.ms.boilerplate.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.peigo.wallet.ms.boilerplate.integrationtest.configuration.IntegrationTestConfiguration
import com.peigo.wallet.ms.boilerplate.model.dto.UserDTO
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@Slf4j
class UsersControllerSpec extends IntegrationTestConfiguration {

    private MockMvc mockMvc

    @Autowired
    UsersControllerImpl usersController

    def setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(usersController)
                .build()
    }

    def "Test DynamoDB Connection"(){
        expect:
        log.info("Is DynamoDBContainer running ??? " + dynamodbContainer.isRunning())
            dynamodbContainer.isRunning()
    }

    def "Test Redis Connection"(){
        expect:
        log.info("Is RedisContainer running ??? " + redisContainer.isRunning())
            redisContainer.isRunning()
    }

    def "Test create user cache"(){
        given:
            log.info("Test createUser Cache")
            def userDTO = UserDTO.builder()
                .age(20)
                .email("correo.prueba@gmail.com")
                .name("Erick Jimenez")
                .build()
            def mapper = new ObjectMapper()
            def response = mockMvc.perform(post("/api/boilerplate/createUser")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .content(new ObjectMapper().writeValueAsString(userDTO)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().response
            def user = mapper.readValue(response.getContentAsByteArray(), UserDTO.class)
            def response2 = mockMvc.perform(post("/api/boilerplate/createUser")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .content(new ObjectMapper().writeValueAsString(userDTO)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse()
            def user2 = mapper.readValue(response2.getContentAsByteArray(), UserDTO.class)
            log.info("user 1 id: " + user.getId() + " - user 2 id: " + user2.getId())
        expect:
            user.getId() == user2.getId()

    }

    def "Create User"() {
        given:
        def userDTO = UserDTO.builder()
                .age(30)
                .email("correo.pruebados@gmail.com")
                .name("Erick")
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

    def "Close DynamoDB Connection"(){
        given:
            dynamodbContainer.stop()
            log.info("Redis connection closed ?? " + !dynamodbContainer.isRunning())
        expect: "DynamoDB connection closed"
            !dynamodbContainer.isRunning()
    }

    def "Close Redis Connection"(){
        given:
            redisContainer.stop()
            log.info("Redis connection closed ?? " + !redisContainer.isRunning())
        expect: "Redis connection closed"
            !redisContainer.isRunning()
    }
}