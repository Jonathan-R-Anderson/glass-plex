package com.example.glassplex.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.glassplex.plex.*
import org.koin.android.ext.android.inject
import androidx.compose.foundation.lazy.LazyRow

class MainActivity: ComponentActivity() {
  private val api by inject<PlexServerApi>()
  private val repo by lazy { PlexRepository(api, "") }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MaterialTheme {
        val token = remember { mutableStateOf("YOUR_PLEX_TOKEN") } // TODO: implement PIN auth
        var home by remember { mutableStateOf<HomeRows?>(null) }
        LaunchedEffect(token.value) {
          if (token.value.isNotBlank()) home = repo.loadHome(token.value)
        }
        Box(Modifier.fillMaxSize().background(Color.Black)) {
          home?.let { HomeScreen(it, token.value) }
        }
      }
    }
  }
}

@Composable
fun HomeScreen(home: HomeRows, token: String) {
  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(16.dp)
  ) {
    item { HeroCard(video = home.hero, token = token) }
    itemsIndexed(home.rows) { _, row ->
      RowSection(title = row.title, videos = row.videos, token = token)
    }
  }
}

@Composable
fun RowSection(title: String, videos: List<Video>, token: String) {
  Column(Modifier.fillMaxWidth()) {
    Text(title, color = Color.White, fontSize = 20.sp)
    Spacer(Modifier.height(8.dp))
    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
      itemsIndexed(videos) { _, v -> PosterCard(video = v, token = token) }
    }
  }
}

@Composable
fun PosterCard(video: Video, token: String) {
  val server = "http://192.168.1.10:32400" // TODO: from settings
  val poster = posterUrl(server, video.thumb, token)
  Column(Modifier.width(160.dp)) {
    Box(Modifier.height(90.dp)) {
      AsyncImage(
        model = poster,
        contentDescription = video.title,
        modifier = Modifier.fillMaxSize().background(Color.DarkGray)
      )
    }
    Text(video.title, color = Color.White, fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
  }
}

@Composable
fun HeroCard(video: Video?, token: String) {
  if (video == null) return
  val server = "http://192.168.1.10:32400" // TODO: from settings
  val art = posterUrl(server, video.art ?: video.thumb, token)
  Box(Modifier.fillMaxWidth().height(160.dp)) {
    AsyncImage(
      model = art,
      contentDescription = video.title,
      modifier = Modifier.matchParentSize().background(Color.DarkGray)
    )
    Box(Modifier.matchParentSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha=0.6f)))))
    Column(Modifier.align(Alignment.BottomStart).padding(16.dp)) {
      Text(video.title, color = Color.White, fontSize = 22.sp)
      Text(video.summary, color = Color.LightGray, fontSize = 12.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
    }
  }
}
