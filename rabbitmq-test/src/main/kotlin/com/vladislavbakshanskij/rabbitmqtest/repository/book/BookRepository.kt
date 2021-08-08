package com.vladislavbakshanskij.rabbitmqtest.repository.book

import com.vladislavbakshanskij.rabbitmqtest.model.Book
import java.util.*

interface BookRepository {
    fun create(title: String, authorId: UUID): Book
    fun findById(id: UUID): Book
    fun update(title: String, id: UUID): Book
    fun deleteById(id: UUID)
}
