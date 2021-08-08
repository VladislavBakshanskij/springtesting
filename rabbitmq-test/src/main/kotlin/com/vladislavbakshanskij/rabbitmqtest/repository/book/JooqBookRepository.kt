package com.vladislavbakshanskij.rabbitmqtest.repository.book

import com.vladislavbakshanskij.rabbitmqtest.jooq.tables.records.BookRecord
import com.vladislavbakshanskij.rabbitmqtest.model.Book
import org.jooq.DSLContext
import org.jooq.RecordMapper
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

import com.vladislavbakshanskij.rabbitmqtest.jooq.tables.Book.BOOK
import org.springframework.stereotype.Repository

@Repository
class JooqBookRepository(@Autowired private var dsl: DSLContext) : BookRepository {
    private var toBookMapper: RecordMapper<BookRecord, Book> = ToBookMapper()

    override fun create(title: String, authorId: UUID): Book {
        val bookId = UUID.randomUUID()
        this.dsl.insertInto(BOOK)
            .set(BOOK.ID, bookId)
            .set(BOOK.TITLE, title)
            .set(BOOK.AUTHOR_ID, authorId)
            .execute()
        return findById(bookId)
    }

    override fun findById(id: UUID): Book {
        val record = this.dsl.selectFrom(BOOK)
            .where(BOOK.ID.eq(id))
            .fetchOne()
        return toBookMapper.map(record) ?: throw RuntimeException("book is null")
    }

    override fun update(title: String, id: UUID): Book {
        this.dsl.update(BOOK)
            .set(BOOK.TITLE, title)
            .where(BOOK.ID.eq(id))
            .execute()
        return findById(id)
    }

    override fun deleteById(id: UUID) {
        this.dsl.deleteFrom(BOOK)
            .where(BOOK.ID.eq(id))
    }

    inner class ToBookMapper : RecordMapper<BookRecord, Book> {
        override fun map(record: BookRecord): Book {
            return Book(record.id, record.title, record.dateCreated.toInstant(), record.authorId)
        }
    }
}
