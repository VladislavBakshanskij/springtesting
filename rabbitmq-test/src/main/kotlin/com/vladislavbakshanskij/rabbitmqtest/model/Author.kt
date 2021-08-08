package com.vladislavbakshanskij.rabbitmqtest.model

import java.time.Instant
import java.util.*

data class Author(val id: UUID, val name: String, val dateBoth: Instant)
