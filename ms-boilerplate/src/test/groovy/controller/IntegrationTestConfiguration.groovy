package controller

import com.peigo.wallet.ms.boilerplate.controller.UsersControllerImpl
import com.peigo.wallet.ms.boilerplate.service.UserServiceImpl
import org.junit.Rule
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.dynamodb.DynaliteContainer
import spock.lang.Specification
import org.testcontainers.spock.Testcontainers

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
abstract class IntegrationTestConfiguration extends Specification {

    private static GenericContainer dynamo = new GenericContainer("amazon/dynamodb-local")

    static {
        System.setProperty("test.server.sql-host", "${dynamo.containerIpAddress}")
        System.setProperty("test.server.sql-port", "${dynamo.firstMappedPort}")
    }

}
