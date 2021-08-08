package com.vladislavbakshanskij.rabbitmqtest.service.author

import com.vladislavbakshanskij.rabbitmqtest.dto.AuthorCreateDTO
import com.vladislavbakshanskij.rabbitmqtest.model.Author
import java.util.*

interface IAuthorService {
    fun create(dto: AuthorCreateDTO): Author
    fun get(id: UUID): Author
    fun update(name: String, id: UUID): Author
    fun delete(id: UUID)
}
