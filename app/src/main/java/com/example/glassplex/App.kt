package com.example.glassplex

import android.app.Application
import com.example.glassplex.plex.PlexServerApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class App: Application() {
  override fun onCreate() {
    super.onCreate()
    val network = module {
      single {
        val log = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        OkHttpClient.Builder().addInterceptor(log).build()
      }
      single { "http://192.168.1.10:32400" } // TODO: make editable in Settings
      single {
        Retrofit.Builder()
          .baseUrl(get<String>())
          .client(get())
          .addConverterFactory(SimpleXmlConverterFactory.create())
          .build()
          .create(PlexServerApi::class.java)
      }
    }
    startKoin { androidContext(this@App); modules(network) }
  }
}
