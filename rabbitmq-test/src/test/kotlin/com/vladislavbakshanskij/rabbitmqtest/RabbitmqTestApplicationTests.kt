package com.vladislavbakshanskij.rabbitmqtest

import com.vladislavbakshanskij.rabbitmqtest.containers.PostgresContainer
import com.vladislavbakshanskij.rabbitmqtest.test.TestUtils
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlMergeMode
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Testcontainers
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(initializers = [RabbitmqTestApplicationTests.Initializer::class])
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(scripts = ["classpath:db/clean.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
@Testcontainers
abstract class RabbitmqTestApplicationTests {
    companion object {
        val DB: GenericContainer<PostgresContainer> = PostgresContainer()
            .withDefaultPort()
    }

    @Autowired
    private lateinit var context: WebApplicationContext

    protected lateinit var mvc: MockMvc
    protected var testUtils = TestUtils()

    @BeforeEach
    fun setUp() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .addFilter<DefaultMockMvcBuilder>({ request: ServletRequest?, response: ServletResponse, chain: FilterChain ->
                response.characterEncoding = "UTF-8"
                chain.doFilter(request, response)
            }, "/*")
            .build()
    }

    inner class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        constructor() {}

        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            DB.start()
            val dbUrl = "jdbc:postgresql://localhost:${DB.getMappedPort(5432)}/postgres";
            TestPropertyValues.of("spring.datasource.url=${dbUrl}")
                .applyTo(applicationContext.environment)

        }
    }
}
