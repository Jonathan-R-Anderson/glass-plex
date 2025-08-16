package com.example.glassplex.plex

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.*
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

private const val PLEX_BASE = "https://plex.tv"

fun plexHeaders(clientId: String) = mapOf(
  "X-Plex-Product" to "GlassPlex",
  "X-Plex-Device" to "Glass",
  "X-Plex-Platform" to "Android",
  "X-Plex-Client-Identifier" to clientId,
  "X-Plex-Version" to "1.0"
)

interface PlexAuthApi {
  @Headers("Accept: application/xml")
  @POST("/pins.xml")
  suspend fun createPin(@HeaderMap headers: Map<String, String>): PinResponse

  @Headers("Accept: application/xml")
  @GET("/pins/{id}.xml")
  suspend fun checkPin(@Path("id") id: Long, @HeaderMap headers: Map<String, String>): PinResponse
}

@Root(name = "pin", strict = false)
data class PinResponse(
  @field:Element(name = "id", required = false) var id: Long = 0,
  @field:Element(name = "code", required = false) var code: String = "",
  @field:Element(name = "authToken", required = false) var authToken: String? = null,
  @field:Element(name = "expiresIn", required = false) var expiresIn: Int = 0
)

fun buildPlexAuthApi(): PlexAuthApi {
  val log = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
  val client = OkHttpClient.Builder().addInterceptor(log).build()
  return Retrofit.Builder()
    .baseUrl(PLEX_BASE)
    .client(client)
    .addConverterFactory(SimpleXmlConverterFactory.create())
    .build()
    .create(PlexAuthApi::class.java)
}
