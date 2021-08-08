package com.vladislavbakshanskij.rabbitmqtest.test

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.lang3.StringUtils
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths


class TestUtils {
    private val objectMapper: ObjectMapper = ObjectMapper()

    fun createPost(url: String?): MockHttpServletRequestBuilder? {
        return MockMvcRequestBuilders.post(url!!, *arrayOfNulls(0)).contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8.name())
    }

    fun <T> readOrThrow(json: String?, clazz: Class<T>?, objectMapper: ObjectMapper): T {
        return try {
            objectMapper.readValue(json, clazz)
        } catch (var4: Throwable) {
            throw var4
        }
    }

    fun readClassPathResourceAsByteArray(path: String?): ByteArray? {
        return try {
            Files.readAllBytes(Paths.get(ClassPathResource(path!!).uri))
        } catch (var2: Throwable) {
            throw var2
        }
    }

    fun readClassPathResourceAsString(path: String?): String? {
        return StringUtils.toEncodedString(readClassPathResourceAsByteArray(path), StandardCharsets.UTF_8)
    }

    fun <T> readClassPathResourceAsObject(path: String?, clazz: Class<T>?, mapper: ObjectMapper): T {
        val json = String(readClassPathResourceAsByteArray(path)!!)
        return readOrThrow(json, clazz, mapper)
    }

    fun <T> readClassPathResourceAsObject(path: String?, clazz: Class<T>?): T {
        return readClassPathResourceAsObject(path, clazz, objectMapper)
    }
}
