package controller

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.auth.InstanceProfileCredentialsProvider
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peigo.wallet.dto.users.UserDTO
import com.peigo.wallet.ms.boilerplate.controller.IUsersController
import com.peigo.wallet.ms.boilerplate.controller.UsersControllerImpl
import com.peigo.wallet.ms.boilerplate.repository.UserRepository
import com.peigo.wallet.ms.boilerplate.service.IUserService;
import com.peigo.wallet.ms.boilerplate.service.UserServiceImpl
import org.junit.internal.runners.rules.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.mock.DetachedMockFactory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@WebMvcTest
class UsersControllerTest extends IntegrationTestConfiguration {

    private MockMvc mockMvc;

    UsersControllerImpl usersControllerImpl;

    UserServiceImpl userService

    def setup() {
        usersControllerImpl = new UsersControllerImpl()
        userService = Mock(UserServiceImpl.class)

        ReflectionTestUtils.setField(usersControllerImpl, "userServiceImpl", userService)

        mockMvc = MockMvcBuilders.standaloneSetup(usersControllerImpl)
                    .build()
    }

    def "Suma de 2 numeros"() {
        expect:
        1 + 1 == 2
    }

    def "varias sumas"() {
        expect:
            def result = suma(a, b)
            result == sumaResult

        where:
            a   |   b   |   sumaResult
            1   |   2   |   3
            1   |   3   |   4
            1   |   4   |   5
            1   |   5   |   6
            1   |   6   |   7
            1   |   7   |   8
            1   |   8   |   9
            1   |   9   |   10
            1   |   10   |  11

    }



    def "Prueba de creacion de usuario"() {
        given:
            def userDTO = UserDTO.builder()
                .age(20)
                .email("correo.prueba@gmail.com")
                .name("Erick Jimenez")
                .build();

        expect:
            mockMvc.perform(post("/createUser")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())

    }
    def "Prueba de consulta de usuarios"() {
        expect:"Get Users"
            mockMvc.perform(get("/getUsers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())

    }

    def suma(int a, int b) {
        return a + b
    }
}
