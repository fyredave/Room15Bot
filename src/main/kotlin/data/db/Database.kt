package data.db

import data.repositories.ConfigRepository
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


class Database(
        private val configRepository: ConfigRepository
) {

    fun connect() {
        val config = configRepository.getDatabaseConfig()
        Database.connect(
                config.url,
                config.driver,
                config.user,
                config.password
        )

        createTables()
    }

    private fun createTables() {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(
                    StarredMessages,
                    Reminders
            )
        }
    }

}

object StarredMessages : LongIdTable() {
    val username = varchar("username", 255)
    val message = varchar("message", 500)
    val stars = integer("stars")
    val permalink = varchar("permalink", 255)
}

object Reminders : IntIdTable() {
    val messageId = long("messageId")
    val triggerAt = long("triggerAt")
    val completed = bool("completed").default(false)
}
