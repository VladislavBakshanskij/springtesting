package com.vladislavbakshanskij.rabbitmqtest.repository.author

import com.vladislavbakshanskij.rabbitmqtest.jooq.tables.Author.AUTHOR
import com.vladislavbakshanskij.rabbitmqtest.jooq.tables.records.AuthorRecord
import com.vladislavbakshanskij.rabbitmqtest.model.Author
import org.jooq.DSLContext
import org.jooq.RecordMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class JooqAuthorRepository(@Autowired private var dsl: DSLContext) : AuthorRepository {
    private var toAuthorMapper: RecordMapper<AuthorRecord, Author> = ToAuthorMapper()

    override fun create(name: String): Author {
        val authorId = UUID.randomUUID()
        this.dsl.insertInto(AUTHOR)
            .set(AUTHOR.ID, authorId)
            .set(AUTHOR.NAME, name)
            .execute()
        return findById(authorId)
    }

    override fun findById(id: UUID): Author {
        val record = this.dsl.selectFrom(AUTHOR)
            .where(AUTHOR.ID.eq(id))
            .fetchOne()
        return toAuthorMapper.map(record) ?: throw RuntimeException("author is null")
    }

    override fun update(name: String, id: UUID): Author {
        this.dsl.update(AUTHOR)
            .set(AUTHOR.NAME, name)
            .execute()
        return findById(id)
    }

    override fun deleteById(id: UUID) {
        this.dsl.deleteFrom(AUTHOR)
            .where(AUTHOR.ID.eq(id))
    }

    inner class ToAuthorMapper : RecordMapper<AuthorRecord, Author> {
        override fun map(record: AuthorRecord): Author {
            return Author(record.id, record.name, record.dateboth.toInstant())
        }

    }
}
