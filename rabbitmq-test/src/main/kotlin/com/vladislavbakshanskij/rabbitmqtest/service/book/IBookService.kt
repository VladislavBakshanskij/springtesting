package com.vladislavbakshanskij.rabbitmqtest.service.book

import com.vladislavbakshanskij.rabbitmqtest.model.Book
import java.util.*

interface IBookService {
    fun create(title: String, authorId: UUID): Book
    fun get(id: UUID): Book
    fun update(title: String, id: UUID): Book
    fun delete(id: UUID)
}
