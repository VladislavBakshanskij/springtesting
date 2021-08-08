package com.vladislavbakshanskij.rabbitmqtest.listener

import com.vladislavbakshanskij.rabbitmqtest.annotation.Listener
import com.vladislavbakshanskij.rabbitmqtest.dto.JournalCreateDTO
import com.vladislavbakshanskij.rabbitmqtest.service.journal.IJournalService
import org.springframework.amqp.rabbit.annotation.*
import org.springframework.beans.factory.annotation.Autowired

@Listener
class JournalListener(@Autowired private var journalService: IJournalService) {
    @RabbitListener(queues = ["journalQueue"])
    fun writeIntoLog(dto: JournalCreateDTO) {
        journalService.create(dto)
    }

    @RabbitListener(queues = ["journalQueue"])
    fun writeIntoLog1(dto: JournalCreateDTO) {
        journalService.create(dto)
    }
}
