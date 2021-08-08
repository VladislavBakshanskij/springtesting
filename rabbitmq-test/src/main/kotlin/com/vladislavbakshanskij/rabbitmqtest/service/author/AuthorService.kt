package com.vladislavbakshanskij.rabbitmqtest.service.author

import com.vladislavbakshanskij.rabbitmqtest.dto.AuthorCreateDTO
import com.vladislavbakshanskij.rabbitmqtest.dto.JournalCreateDTO
import com.vladislavbakshanskij.rabbitmqtest.jooq.tables.Author.AUTHOR
import com.vladislavbakshanskij.rabbitmqtest.model.Author
import com.vladislavbakshanskij.rabbitmqtest.repository.author.AuthorRepository
import org.springframework.amqp.core.AsyncAmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.concurrent.ListenableFutureCallback
import java.util.*

@Service
@Transactional
class AuthorService(
    @Autowired private var authorRepository: AuthorRepository,
    @Autowired private var asyncRabbitTemplate: AsyncAmqpTemplate
) : IAuthorService {
    override fun create(dto: AuthorCreateDTO): Author {
        val name = dto.name ?: throw RuntimeException("name is null")
        val author = authorRepository.create(name)
        val future = asyncRabbitTemplate.convertSendAndReceive<JournalCreateDTO>(
            "journalExchange", "journalQueue", JournalCreateDTO("create", AUTHOR.name, author)
        )
        future.addCallback(AuthorListenableFutureCallback())
        return author
    }

    override fun get(id: UUID): Author {
        return authorRepository.findById(id)
    }

    override fun update(name: String, id: UUID): Author {
        val author = authorRepository.update(name, id)
        val future = asyncRabbitTemplate.convertSendAndReceive<JournalCreateDTO>(
            "journalExchange", "journalQueue", JournalCreateDTO("update", AUTHOR.name, author)
        )
        return author
    }

    override fun delete(id: UUID) {
        authorRepository.deleteById(id)
    }

    inner class AuthorListenableFutureCallback : ListenableFutureCallback<JournalCreateDTO> {
        override fun onSuccess(result: JournalCreateDTO?) {
            println("EEEEEEE")
        }

        override fun onFailure(ex: Throwable) {
            println("WWWWW")
        }
    }
}
