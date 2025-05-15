package com.cmloopy.lumitel.manager

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.CacheWriter
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.cmloopy.lumitel.LumitelApp
import kotlinx.coroutines.*

@UnstableApi
object ExoPlayerManager {
    private var exoPlayer: ExoPlayer? = null
    private var cacheDataSourceFactory: CacheDataSource.Factory? = null
    fun getPlayer(context: Context): ExoPlayer {
        if (exoPlayer == null) {
            val dataSourceFactory = DefaultDataSource.Factory(context)
            cacheDataSourceFactory = CacheDataSource.Factory()
                .setCache(LumitelApp.simpleCache)
                .setUpstreamDataSourceFactory(dataSourceFactory)
                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
            exoPlayer = ExoPlayer.Builder(context)
                .setMediaSourceFactory(DefaultMediaSourceFactory(cacheDataSourceFactory!!))
                .build()
        }
        return exoPlayer!!
    }
    fun releasePlayer() {
        exoPlayer?.release()
        exoPlayer = null
    }
    fun pauseVideo() {
        exoPlayer?.pause()
    }
    fun resumeVideo(){
        exoPlayer?.play()
    }
    fun play(mediaItem: MediaItem) {
        exoPlayer?.setMediaItem(mediaItem)
        exoPlayer?.prepare()
        exoPlayer?.repeatMode = Player.REPEAT_MODE_ALL
        exoPlayer?.playWhenReady = true
    }
    fun preloadVideo(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val dataSpec = DataSpec(Uri.parse(url))
            val cacheWriter = CacheWriter(
                cacheDataSourceFactory!!.createDataSource(),
                dataSpec,
                null,
                null
            )
            try {
                cacheWriter.cache()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
