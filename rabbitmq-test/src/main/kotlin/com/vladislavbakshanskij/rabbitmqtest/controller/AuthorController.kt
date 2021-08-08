package com.vladislavbakshanskij.rabbitmqtest.controller

import com.vladislavbakshanskij.rabbitmqtest.dto.AuthorCreateDTO
import com.vladislavbakshanskij.rabbitmqtest.dto.AuthorDTO
import com.vladislavbakshanskij.rabbitmqtest.service.author.IAuthorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/authors")
class AuthorController(@Autowired private var authorService: IAuthorService) {
    @PostMapping
    fun create(@RequestBody dto: AuthorCreateDTO): AuthorDTO {
        val author = authorService.create(dto)
        return AuthorDTO(author.id, author.name, author.dateBoth)
    }
}
