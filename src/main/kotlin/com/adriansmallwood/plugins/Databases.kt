package com.adriansmallwood.plugins

import com.adriansmallwood.database.ArtistsTable
import com.adriansmallwood.database.FeaturesTable
import com.adriansmallwood.database.TracksTable
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val database = Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = ""
    )
    transaction {
        SchemaUtils.createMissingTablesAndColumns(TracksTable, ArtistsTable, FeaturesTable)
    }
}
