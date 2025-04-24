package com.cmloopy.lumitel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.cmloopy.lumitel.data.models.video.Video

class VideoViewModelRemake(private val context: Context): ViewModel() {
    private val players = mutableListOf<ExoPlayer>()
    private var currentIndex = -1
    private var currentPage = -1
    init {
        repeat(3) {
            players.add(ExoPlayer.Builder(context).build())
        }
    }
    fun onPageSelected(position: Int, videoList: List<Video>) {
        if (position == currentPage) return
        val direction = position - currentPage
        currentPage = position

        if (direction != 0) {
            rotatePlayers(direction)
        }

        val prevVideo = videoList.getOrNull(position - 1)
        val currVideo = videoList.getOrNull(position)
        val nextVideo = videoList.getOrNull(position + 1)

        players[0].apply {
            stop()
            clearMediaItems()
            prevVideo?.let {
                setMediaItem(MediaItem.fromUri(it.videoMedia))
                prepare()
                playWhenReady = false
            }
        }

        players[1].apply {
            stop()
            clearMediaItems()
            currVideo?.let {
                setMediaItem(MediaItem.fromUri(it.videoMedia))
                prepare()
                playWhenReady = true
            }
        }

        players[2].apply {
            stop()
            clearMediaItems()
            nextVideo?.let {
                setMediaItem(MediaItem.fromUri(it.videoMedia))
                prepare()
                playWhenReady = false
            }
        }
    }
    private fun rotatePlayers(direction: Int) {
        if (direction > 0) {
            // Scroll xuống
            val first = players.removeAt(0)
            players.add(first)
        } else {
            // Scroll lên
            val last = players.removeAt(2)
            players.add(0, last)
        }
    }
    fun bindPlayerTo(holder: VideoViewAdapter.VideoViewHolder, position: Int) {
        /*when (position) {
            currentPage - 1 -> holder.binding.playerView.player = players[0]
            currentPage -> holder.binding.playerView.player = players[1]
            currentPage + 1 -> holder.binding.playerView.player = players[2]
            else -> holder.binding.playerView.player = null
        }*/
    }
    override fun onCleared() {
        players.forEach { it.release() }
    }
}