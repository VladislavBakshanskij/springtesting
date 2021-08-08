package com.vladislavbakshanskij.rabbitmqtest.repository.journal

import com.vladislavbakshanskij.rabbitmqtest.model.Journal
import java.util.*

interface JournalRepository {
    fun create(action: String, tableName: String, objectState: Any): Journal
    fun findById(id: UUID): Journal
}
