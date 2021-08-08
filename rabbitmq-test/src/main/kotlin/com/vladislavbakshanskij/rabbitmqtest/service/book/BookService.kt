package com.vladislavbakshanskij.rabbitmqtest.service.book

import com.vladislavbakshanskij.rabbitmqtest.model.Book
import com.vladislavbakshanskij.rabbitmqtest.repository.book.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class BookService(@Autowired private var bookRepository: BookRepository) : IBookService {
    override fun create(title: String, authorId: UUID): Book {
        return bookRepository.create(title, authorId)
    }

    override fun get(id: UUID): Book {
        return bookRepository.findById(id)
    }

    override fun update(title: String, id: UUID): Book {
        return bookRepository.update(title, id)
    }

    override fun delete(id: UUID) {
        bookRepository.deleteById(id)
    }
}
