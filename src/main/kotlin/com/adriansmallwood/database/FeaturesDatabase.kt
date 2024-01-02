package com.adriansmallwood.database

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object ArtistsTable: IntIdTable() {
    val spotifyId = text("spotify_id").uniqueIndex()
    val name = text("name")
    val lastAlbumSynced = text("last_album_synced").nullable()
    val syncedAt = datetime("synced_at").nullable()
}

class Artist(id: EntityID<Int>) : IntEntity(id) {
    var spotifyId by ArtistsTable.spotifyId
    var name by ArtistsTable.name
    var lastAlbumSynced by ArtistsTable.lastAlbumSynced
    var syncedAt by ArtistsTable.syncedAt

    companion object : IntEntityClass<Artist>(ArtistsTable)
}

object FeaturesTable: Table() {
    val trackId = reference("track_id", TracksTable)
    val artistId = reference("artist_id", ArtistsTable)

    override val primaryKey = PrimaryKey(trackId, artistId)
}

object TracksTable: IntIdTable() {
    val spotifyId = text("spotify_id").uniqueIndex()
    val name = text("name")
}

class Track(id: EntityID<Int>) : IntEntity(id) {
    var spotifyId by TracksTable.spotifyId
    var name by TracksTable.name

    companion object : IntEntityClass<Track>(TracksTable)
}