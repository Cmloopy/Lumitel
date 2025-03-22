package com.cmloopy.lumitel.utils

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri

object VideoThumb {
    /*fun getVideoFrame(videoUrl: String, timeMs: Long): Bitmap? {
        val retriever = MediaMetadataRetriever()
        return try {
            retriever.setDataSource(videoUrl, HashMap())
            retriever.getFrameAtTime(timeMs * 1000, MediaMetadataRetriever.OPTION_CLOSEST)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            retriever.release()
        }
    }*/
    fun getVideoFrame(context: Context, rawResId: Int, timeMs: Long): Bitmap? {
        val retriever = MediaMetadataRetriever()
        return try {
            val uri = Uri.parse("android.resource://${context.packageName}/$rawResId")
            retriever.setDataSource(context, uri)
            retriever.getFrameAtTime(timeMs * 1000, MediaMetadataRetriever.OPTION_CLOSEST)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            retriever.release()
        }
    }

}