package com.vladislavbakshanskij.rabbitmqtest.repository.journal

import com.fasterxml.jackson.databind.ObjectMapper
import com.vladislavbakshanskij.rabbitmqtest.jooq.tables.Journal.JOURNAL
import com.vladislavbakshanskij.rabbitmqtest.jooq.tables.records.JournalRecord
import com.vladislavbakshanskij.rabbitmqtest.model.Journal
import org.jooq.DSLContext
import org.jooq.JSONB
import org.jooq.RecordMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class JooqJournalRepository(@Autowired private var dsl: DSLContext) : JournalRepository {
    companion object var objectMapper = ObjectMapper()
    private var toJournalMapper: RecordMapper<JournalRecord, Journal> = ToJournalMapper()

    override fun create(action: String, tableName: String, objectState: Any): Journal {
        val id = UUID.randomUUID()
        this.dsl.insertInto(JOURNAL)
            .set(JOURNAL.ID, id)
            .set(JOURNAL.ACTION, action)
            .set(JOURNAL.TABLENAME, tableName)
            .set(JOURNAL.OBJECTSTATE, JSONB.jsonbOrNull(objectMapper.writeValueAsString(objectState)))
            .execute()
        return findById(id)
    }

    override fun findById(id: UUID): Journal {
        val record = this.dsl.selectFrom(JOURNAL)
            .where(JOURNAL.ID.eq(id))
            .fetchOne()
        return toJournalMapper.map(record) ?: throw RuntimeException("record is null")
    }

    inner class ToJournalMapper : RecordMapper<JournalRecord, Journal> {
        override fun map(record: JournalRecord): Journal {
            val objectState = objectMapper.readValue(record.objectstate.data(), Any::class.java)
            return Journal(record.id, record.action, record.tablename, objectState, record.updateDate.toInstant())
        }
    }
}
