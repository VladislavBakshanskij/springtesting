package com.vladislavbakshanskij.rabbitmqtest.dto

data class JournalCreateDTO(var action: String, var tableName: String, var objectState: Any)
