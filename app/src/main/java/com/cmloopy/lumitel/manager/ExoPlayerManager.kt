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
    private var currentUrl: String? = null

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

    fun play(context: Context, videoUrl: String) {
        val player = getPlayer(context)

        if (currentUrl != videoUrl) {
            val mediaItem = MediaItem.fromUri(videoUrl)
            val mediaSource = DefaultMediaSourceFactory(cacheDataSourceFactory!!).createMediaSource(mediaItem)
            player.setMediaSource(mediaSource)
            /*player.setMediaItem(mediaItem)*/
            player.prepare()
            currentUrl = videoUrl
        }

        player.repeatMode = Player.REPEAT_MODE_ALL
        player.playWhenReady = true
    }

    fun pause() {
        exoPlayer?.pause()
    }
    fun resume() {
        exoPlayer?.play()
    }
    fun release() {
        exoPlayer?.release()
        exoPlayer = null
        currentUrl = null
    }

    fun preloadVideo(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (isVideoCached(url)) return@launch

                val dataSpec = DataSpec.Builder()
                    .setUri(Uri.parse(url))
                    .setLength(1 * 1024 * 1024)
                    .build()
                val cacheWriter = CacheWriter(
                    cacheDataSourceFactory!!.createDataSource(),
                    dataSpec,
                    null,
                    null
                )
                cacheWriter.cache()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun isVideoCached(url: String): Boolean {
        val uri = Uri.parse(url)
        val key = uri.toString()
        val spans = LumitelApp.simpleCache.getCachedSpans(key)
        var cachedBytes = 0L
        for (span in spans) {
            cachedBytes += span.length
        }
        return cachedBytes > 100 * 1024
    }

}
