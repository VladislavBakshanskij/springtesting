package com.vladislavbakshanskij.rabbitmqtest.repository.author

import com.vladislavbakshanskij.rabbitmqtest.model.Author
import java.util.*

interface AuthorRepository {
    fun create(name: String): Author
    fun findById(id: UUID): Author
    fun update(name: String, id: UUID): Author
    fun deleteById(id: UUID)
}
