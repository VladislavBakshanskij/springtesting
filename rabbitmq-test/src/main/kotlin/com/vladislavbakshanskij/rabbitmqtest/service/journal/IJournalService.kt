package com.vladislavbakshanskij.rabbitmqtest.service.journal

import com.vladislavbakshanskij.rabbitmqtest.dto.JournalCreateDTO
import com.vladislavbakshanskij.rabbitmqtest.model.Journal

interface IJournalService {
    fun create(dto: JournalCreateDTO): Journal
}
