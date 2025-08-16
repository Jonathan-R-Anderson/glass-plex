package com.example.glassplex.ui.player

import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

class PlayerActivity: ComponentActivity() {
  private var player: SimpleExoPlayer? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    val playerView = PlayerView(this)
    setContentView(playerView)

    val url = intent.getStringExtra("STREAM_URL") ?: return
    player = SimpleExoPlayer.Builder(this).build().also {
      playerView.player = it
      it.setMediaItem(MediaItem.fromUri(Uri.parse(url)))
      it.prepare(); it.playWhenReady = true
    }
  }

  override fun onStop() {
    super.onStop()
    player?.release(); player = null
  }
}
