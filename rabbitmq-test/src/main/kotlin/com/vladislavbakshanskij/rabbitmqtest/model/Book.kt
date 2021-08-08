package com.vladislavbakshanskij.rabbitmqtest.model

import java.time.Instant
import java.util.*

data class Book(val id: UUID, val title: String, val dateCreated: Instant, val authorId: UUID)
