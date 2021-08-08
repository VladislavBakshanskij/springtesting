package com.vladislavbakshanskij.rabbitmqtest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [R2dbcAutoConfiguration::class])
class RabbitmqTestApplication

fun main(args: Array<String>) {
    runApplication<RabbitmqTestApplication>(*args)
}
