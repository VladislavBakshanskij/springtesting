package com.vladislavbakshanskij.rabbitmqtest.dto

import java.time.Instant
import java.util.*

data class AuthorDTO(val id: UUID, val name: String, val dateBoth: Instant)
