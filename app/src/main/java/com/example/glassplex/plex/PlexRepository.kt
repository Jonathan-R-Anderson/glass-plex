package com.example.glassplex.plex

class PlexRepository(private val api: PlexServerApi, private val serverBase: String) {
  suspend fun loadHome(token: String): HomeRows = HomeRows(
    hero = pickHero(token),
    rows = listOf(
      Row("Continue Watching", api.onDeck(token).videos.orEmpty()),
      Row("Recently Added", api.recentlyAdded(token).videos.orEmpty())
    ) + sectionRows(token)
  )

  private suspend fun pickHero(token: String) =
    api.recentlyAdded(token).videos?.firstOrNull()

  private suspend fun sectionRows(token: String): List<Row> {
    val secs = api.sections(token).directories.orEmpty()
    return secs.take(4).map { sec -> Row(sec.title, api.itemsInSection(sec.key, token).videos.orEmpty()) }
  }
}

data class Row(val title: String, val videos: List<Video>)
data class HomeRows(val hero: Video?, val rows: List<Row>)
