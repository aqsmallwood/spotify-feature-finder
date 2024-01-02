package com.adriansmallwood.services

import com.adriansmallwood.database.*
import com.adriansmallwood.models.Feature
import com.adriansmallwood.models.FeatureSearchArtist
import io.ktor.client.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Clock
import java.time.LocalDateTime

//val features = listOf(
//    Feature("1", "They Ready", listOf(bigkrit.id, jcole.id, kendrick.id)),
//    Feature("2", "Poetic Justice", listOf(drake.id, kendrick.id)),
//    Feature("3", "First Person Shooter Mode", listOf(drake.id, jcole.id)),
//    Feature("4", "Swerve", listOf(webbie.id, boosie.id)),
//    Feature("5", "Rapper Shit", listOf(kendrick.id, absoul.id)),
//    Feature("6", "Evil Ways", listOf(drake.id, jcole.id)),
//)

class FeatureService(
    private val client: HttpClient,
    private val albumService: AlbumService,
    private val trackService: TrackService,
    private val tokenService: TokenService,
) {
    private val featuresCache = mutableMapOf<String, Set<Feature>>()
    private val artistsCache = mutableMapOf<String, Set<Feature>>()
    val syncedCache = mutableMapOf<String, Pair<String, LocalDateTime>>()

    suspend fun getFeatures(artists: List<FeatureSearchArtist>): Set<Feature> {
        val artists = artists.sortedBy { it.artistSpotifyId }

        val featuresKey = artists.map { it.artistSpotifyId }.joinToString("-")
        val cachedFeatures = featuresCache[featuresKey]
        if (cachedFeatures != null) return setOf()

        val artistsFeatures = artists.associateWith { artist ->
            artistsCache[artist.artistSpotifyId] ?: getFeaturesForArtist(artist).also { artistsCache[artist.artistSpotifyId] = it }
        }
        val features = artistsFeatures.values.reduce { a, b ->
            a.intersect(b)
        }

        return features
    }

    private suspend fun getFeaturesForArtist(searchArtist: FeatureSearchArtist): Set<Feature> {
        // loop through all artists albums and for any albums with more than one artist, note the track ids
        val artist = transaction {
            Artist
                .find { ArtistsTable.spotifyId eq searchArtist.artistSpotifyId }
                .firstOrNull() ?: Artist.new {
                spotifyId = searchArtist.artistSpotifyId
                name = searchArtist.artistName
            }
        }
        val lastSyncedAlbum = artist.lastAlbumSynced
        val syncedAt = artist.syncedAt

        fun getFromDatabase(): Set<Feature> {
            return transaction {
                    ArtistsTable
                    .innerJoin(FeaturesTable, { ArtistsTable.id }, { FeaturesTable.artistId })
                    .innerJoin(TracksTable, { FeaturesTable.trackId }, { TracksTable.id })
                    .slice(TracksTable.id, TracksTable.spotifyId, TracksTable.name)
                    .select { (ArtistsTable.spotifyId eq searchArtist.artistSpotifyId) and (ArtistsTable.id eq FeaturesTable.artistId) }
                    .map {
                        Feature(
                            it[TracksTable.id].toString(),
                            it[TracksTable.name],
                        )
                    }.toSet()
            }
            // Luh Tyler 1K15GRZZATsCJyGJ4bYiEz
            // AntiDaMenace 7jkEdcZtIMWXlEM5sgZ2uK
        }

        // if it's been less than a day since the last sync than return features from database
        if (syncedAt != null && syncedAt.isBefore(LocalDateTime.now(Clock.systemUTC()).minusDays(1))) {
            println("Recently synced - Returning ${artist.name} features from database")
            return getFromDatabase()
        }

        val newAlbumIds = albumService.getAlbums(artist.spotifyId, lastSyncedAlbum)
        println("Fetching albums for ${artist.name}")
        val newAlbumFeatures = newAlbumIds.flatMap {
            trackService.getFeaturesForAlbum(it, artist.spotifyId)
        }
        val newLastSyncedAlbumId = newAlbumIds.firstOrNull()

        transaction {
            newAlbumFeatures.forEach { trackResponseItem ->
                val track = Track.find { TracksTable.spotifyId eq trackResponseItem.id }.firstOrNull() ?: Track.new {
                    spotifyId = trackResponseItem.id
                    name = trackResponseItem.name
                }
                FeaturesTable.insert {
                    it[trackId] = track.id
                    it[artistId] = artist.id
                }
            }
        }

        transaction {
            if (newLastSyncedAlbumId != null) {
                artist.lastAlbumSynced = newLastSyncedAlbumId
                artist.syncedAt = LocalDateTime.now(Clock.systemUTC())
            }
        }

        return getFromDatabase()
    }

    private suspend fun getFeaturesForArtistsAndAlbums(artists: List<String>, albumIds: List<String>): List<Feature> {
        val features = mutableListOf<Feature>()

        albumIds.forEach { albumId ->
            val albumTracks = trackService.getFeaturesForAlbum(albumId, "")
            val albumFeatures = albumTracks.filter { track ->
                track.artists.map { it.id }.containsAll(artists)
            }.map { track ->
                Feature(track.id, track.name)
            }
            features.addAll(albumFeatures)
        }
        return features
    }
}
