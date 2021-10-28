package com.peigo.wallet.ms.boilerplate.controller


import com.fasterxml.jackson.databind.ObjectMapper
import com.peigo.wallet.ms.boilerplate.integrationtest.configuration.IntegrationTestConfiguration
import com.peigo.wallet.ms.boilerplate.model.dto.UserDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class UsersControllerSpec extends IntegrationTestConfiguration {
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

    def "Create User With DTO"() {
        given:
            def userDTO = UserDTO.builder()
                    .documentNumber("1234567890")
                    .email("correo.prueba@gmail.com")
                    .mobile(3003003000)
                    .password("123")
                    .name("Erick Jimenez")
                    .age(20)
                    .build()
        expect:
            mockMvc.perform(post("/api/boilerplate/createUser")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .content(new ObjectMapper().writeValueAsString(userDTO)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
    }

    def "Create User With JSON"() {
        given:
            def testUser = """
                                    {
                                        "documentNumber": "71234234",
                                        "mobile": 3003003001,
                                        "password": "321",
                                        "name": "Lisandro Gómez",
                                        "age": 38,
                                        "email": "correo.prueba@globant.com"
                                    }
                                
                                """
        expect:
            mockMvc.perform(post("/api/boilerplate/createUser")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .content(testUser))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
    }

    def "Get Users"() {
        expect:
            getUsers()
            .andExpect(MockMvcResultMatchers.status().is(200))
            .andExpect(MockMvcResultMatchers.jsonPath('$.[0].name').isString())
            .andExpect(MockMvcResultMatchers.jsonPath('$.[0].name').isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath('$.[0].name').value("ERICK JIMENEZ"))
            .andExpect(MockMvcResultMatchers.jsonPath('$.[0].email').value("correo.prueba@gmail.com"))
            .andExpect(MockMvcResultMatchers.jsonPath('$.[0].age').value(20))
            .andDo(MockMvcResultHandlers.print())
    }
    def getUsers() {
        mockMvc.perform(get("/api/boilerplate/getUsers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))

    }
}