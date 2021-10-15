package controller;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peigo.wallet.dto.users.UserDTO
import com.peigo.wallet.ms.boilerplate.controller.UsersControllerImpl;
import com.peigo.wallet.ms.boilerplate.service.UserServiceImpl
import org.junit.internal.runners.rules.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @MockBean
    private AmazonDynamoDB amazonDynamoDB;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @MockBean
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    ValidationError validationError

    UserServiceImpl userService


    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UsersControllerImpl())
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



//    def "Prueba de creación de usuario"() {
//        given:
//        def userDTO = UserDTO.builder()
//                .age(20)
//                .email("correo.prueba@gmail.com")
//                .name("Erick Jimenez")
//                .build();
////        def userDTO = new UserDTO("Erick Jimenez2","correo.prueba@gmail.com",  20)
//        println("Test Lisandro")
//        println new ObjectMapper().writeValueAsString(userDTO)
//        expect:
//        mockMvc.perform(post("/createUser")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .content(new ObjectMapper().writeValueAsString(userDTO)))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//    }
//    def "Prueba de consulta de usuarios"() {
//        expect:
//        doGet("/getUsers" )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print())
//
////        mockMvc.perform(get("/getUsers")
////        .contentType(MediaType.APPLICATION_JSON_VALUE)
////        .accept(MediaType.APPLICATION_JSON_VALUE))
////        .andExpect(MockMvcResultMatchers.status().isOk());
//
//    }
//
//    @Test
//    @DisplayName("Prueba de creación de usuario")
//    public void createUser_OK() throws Exception {
//        UserDTO userDTO = UserDTO.builder()
//                .age(20)
//                .email("correo.prueba@gmail.com")
//                .name("Erick Jimenez")
//                .build();
//        mockMvc.perform(post("/createUser")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .content(new ObjectMapper().writeValueAsString(userDTO)))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @Test
//    @DisplayName("Prueba de consulta de usuarios")
//    public void getUsers_OK() throws Exception {
//        mockMvc.perform(get("/getUsers")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }

    private doGet(String url) {
        mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .header("X-TenantId", "CLARIN")
                        .contentType(MediaType.APPLICATION_JSON)
        )
    }
    private doPost(String url, String body) {
        mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header("X-TenantId", "CLARIN")
                .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        )
    }

    @TestConfiguration
    static class MockConfig {
        def detachedMockFactory = new DetachedMockFactory()

        @Bean
        UserServiceImpl getUsersService() {
            return detachedMockFactory.Stub(UserServiceImpl)
        }
    }

    def suma(int a, int b) {
        return a + b
    }
}
