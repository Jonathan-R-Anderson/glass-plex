# Glass Plex

An experimental Android application for browsing and streaming media from a Plex server.

## Building

The project uses Gradle with the Android plugin. You can build from the command line:

```bash
gradle build
```

Or open the project in Android Studio and run it on a device or emulator.

## Configuring Plex

Two pieces of information are required to stream from your Plex Media Server:

1. **Server address** – Update `SERVER_BASE` in [`PlexConfig.kt`](app/src/main/java/com/example/glassplex/PlexConfig.kt) to the base URL of your Plex server (for example `http://192.168.1.10:32400`).
2. **Authentication token** – In [`MainActivity.kt`](app/src/main/java/com/example/glassplex/ui/MainActivity.kt) replace the `"YOUR_PLEX_TOKEN"` placeholder with a valid Plex token or implement the PIN flow in `PlexAuth.kt` to retrieve one.

After these values are set, running the app will allow you to browse your libraries and tap a poster to start playback using ExoPlayer.
