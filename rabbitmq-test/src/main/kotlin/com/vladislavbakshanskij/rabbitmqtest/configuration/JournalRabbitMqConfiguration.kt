package com.vladislavbakshanskij.rabbitmqtest.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.AsyncRabbitTemplate
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class JournalRabbitMqConfiguration {
    @Bean
    fun journalQueue(): Queue {
        return Queue("journalQueue")
    }

    @Bean
    fun journalExchange(): Exchange {
        return DirectExchange("journalExchange")
    }

    @Bean
    fun bindJournalExchangeWithJournalQueue(): Binding {
        return BindingBuilder.bind(journalQueue())
            .to(journalExchange())
            .with("journalQueue")
            .noargs()
    }

    @Bean
    fun messageConverter(@Autowired objectMapper: ObjectMapper): MessageConverter {
        return Jackson2JsonMessageConverter(objectMapper)
    }

    @Bean
    fun asyncRabbitTemplate(@Autowired rabbitTemplate: RabbitTemplate): AsyncAmqpTemplate {
        return AsyncRabbitTemplate(rabbitTemplate)
    }
}
