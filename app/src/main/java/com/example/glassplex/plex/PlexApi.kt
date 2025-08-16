package com.example.glassplex.plex

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface PlexServerApi {
  @GET("/library/onDeck")
  suspend fun onDeck(@Header("X-Plex-Token") token: String): MediaContainer

  @GET("/library/recentlyAdded")
  suspend fun recentlyAdded(@Header("X-Plex-Token") token: String): MediaContainer

  @GET("/library/sections")
  suspend fun sections(@Header("X-Plex-Token") token: String): MediaContainer

  @GET("/library/sections/{key}/all")
  suspend fun itemsInSection(
    @Path("key") key: String,
    @Header("X-Plex-Token") token: String,
    @Query("type") type: Int? = null
  ): MediaContainer

  @GET("/hubs/home")
  suspend fun hubs(@Header("X-Plex-Token") token: String): MediaContainer
}

@Root(name = "MediaContainer", strict = false)
data class MediaContainer(
  @field:ElementList(entry = "Directory", inline = true, required = false)
  var directories: List<Directory>? = null,
  @field:ElementList(entry = "Video", inline = true, required = false)
  var videos: List<Video>? = null
)

@Root(name = "Directory", strict = false)
data class Directory(
  @field:Element(name = "title", required = false) var title: String = "",
  @field:Element(name = "key", required = false) var key: String = "",
  @field:Element(name = "thumb", required = false) var thumb: String? = null
)

@Root(name = "Video", strict = false)
data class Video(
  @field:Element(name = "ratingKey", required = false) var ratingKey: String = "",
  @field:Element(name = "title", required = false) var title: String = "",
  @field:Element(name = "summary", required = false) var summary: String = "",
  @field:Element(name = "thumb", required = false) var thumb: String? = null,
  @field:Element(name = "art", required = false) var art: String? = null,
  @field:Element(name = "duration", required = false) var durationMs: Long = 0
)
