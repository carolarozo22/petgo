package controller

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
abstract class IntegrationTestConfiguration extends Specification {


    static {

    }
}
