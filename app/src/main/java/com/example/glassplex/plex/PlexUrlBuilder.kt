package com.example.glassplex.plex

fun posterUrl(server: String, path: String?, token: String): String? =
  path?.let { "$server$it?X-Plex-Token=$token" }

fun streamUrlHls(server: String, ratingKey: String, token: String, maxRes: String = "720"): String =
  "$server/video/:/transcode/universal/start.m3u8?X-Plex-Token=$token&path=%2Flibrary%2Fmetadata%2F$ratingKey&maxVideoBitrate=4000&videoResolution=${maxRes}p&protocol=hls"
