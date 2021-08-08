package com.vladislavbakshanskij.rabbitmqtest.service.journal

import com.vladislavbakshanskij.rabbitmqtest.dto.JournalCreateDTO
import com.vladislavbakshanskij.rabbitmqtest.model.Journal
import com.vladislavbakshanskij.rabbitmqtest.repository.journal.JournalRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class JournalService(@Autowired private var journalRepository: JournalRepository) : IJournalService {
    override fun create(dto: JournalCreateDTO): Journal {
        return journalRepository.create(dto.action, dto.tableName, dto.objectState)
    }
}
