package com.vladislavbakshanskij.rabbitmqtest.containers

import org.testcontainers.containers.GenericContainer

class PostgresContainer : GenericContainer<PostgresContainer> {
    constructor() : super("postgres:13.3-alpine") {
    }

    fun withDefaultPort(): PostgresContainer {
        return withExposedPorts(5432)
    }

    fun withDb(db: String): PostgresContainer {
        return withEnv("POSTGRES_DB", db)
    }

    fun withUsername(username: String): PostgresContainer {
        return withEnv("POSTGRES_USER", username)
    }

    fun withPassword(password: String): PostgresContainer {
        return withEnv("POSTGRES_PASSWORD", password)
    }
}
