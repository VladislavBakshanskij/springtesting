package com.vladislavbakshanskij.rabbitmqtest.controller

import com.vladislavbakshanskij.rabbitmqtest.RabbitmqTestApplicationTests
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional


class AuthorControllerTest : RabbitmqTestApplicationTests() {
    @Test
    @Transactional
    fun createSuccessTest() {
        val request: String = buildJson("olegRequest.json")
        val response: String = buildJson("olegResponse.json")

        testUtils
            .createPost("/authors")?.let {
                mvc.perform(it.content(request))
                    .andDo(print())
                    .andExpect(content().json(response, false))
                    .andExpect(status().`is`(HttpStatus.OK.value()))
            }
    }

    fun buildJson(resource: String, vararg args: Any): String {
        val template: String = testUtils
            .readClassPathResourceAsString("json/ProjectJournalControllerTest/$resource") ?: throw RuntimeException()
        return String.format(template, *args)
    }
}
