package com.vladislavbakshanskij.rabbitmqtest.model

import java.time.Instant
import java.util.*

data class Journal(
    val id: UUID,
    val action: String,
    val tableName: String,
    val objectState: Any,
    val updateDate: Instant
)
