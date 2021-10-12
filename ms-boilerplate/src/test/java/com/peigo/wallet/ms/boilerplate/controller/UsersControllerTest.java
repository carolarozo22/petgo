package com.peigo.wallet.ms.boilerplate.controller;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peigo.wallet.ms.boilerplate.dto.UserDTO;
import com.peigo.wallet.ms.boilerplate.service.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AmazonDynamoDB amazonDynamoDB;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @MockBean
    private DynamoDBMapper dynamoDBMapper;

    @Test
    @DisplayName("Prueba de creación de usuario")
    public void createUser_OK() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .age(20)
                .email("correo.prueba@gmail.com")
                .name("Erick Jimenez")
                .build();
        mockMvc.perform(post("/createUser")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Prueba de consulta de usuarios")
    public void getUsers_OK() throws Exception {
        mockMvc.perform(get("/getUsers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
