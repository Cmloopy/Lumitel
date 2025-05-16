package com.cmloopy.lumitel.zsingleexo

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cmloopy.lumitel.databinding.ActivityVideoViewRemakeBinding
import com.cmloopy.lumitel.manager.ExoPlayerManager

@Suppress("DEPRECATION")
@UnstableApi
class VideoViewActivityRemake : AppCompatActivity() {
    private lateinit var binding: ActivityVideoViewRemakeBinding
    private val viewModel: VideoViewModelRemake by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoViewRemakeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        viewModel.videos.observe(this){
            val adapterRemake = VideoViewAdapterRemake(it, this){
                viewModel.rotateVideo()
            }
            binding.vpgShortVideo.adapter = adapterRemake
            binding.vpgShortVideo.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.vpgShortVideo.post {
                        val recyclerView = binding.vpgShortVideo.getChildAt(0) as? RecyclerView
                        val holder = recyclerView?.findViewHolderForAdapterPosition(position)
                                as? VideoViewAdapterRemake.VideoViewHolder

                        val video = adapterRemake.getVideo(position) ?: return@post

                        holder?.playerView?.player = ExoPlayerManager.getPlayer(this@VideoViewActivityRemake)
                        ExoPlayerManager.play(this@VideoViewActivityRemake,video.videoMedia)

                        adapterRemake.getVideo(position + 1)?.let {url ->
                            ExoPlayerManager.preloadVideo(url.videoMedia)
                        }
                        adapterRemake.getVideo(position - 1)?.let {url ->
                            ExoPlayerManager.preloadVideo(url.videoMedia)
                        }
                    }
                }
            })
        }
        viewModel.isRotate.observe(this){
            rotate(it)
        }
    }
    private fun rotate(b: Boolean) {
        if(b){
            hideSystemUI()
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            binding.btnDropBack.visibility = View.GONE
            binding.btnCreateNew.visibility = View.GONE
            binding.vpgShortVideo.isUserInputEnabled = false
        }
        else{
            showSystemUI()
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            binding.btnDropBack.visibility = View.VISIBLE
            binding.btnCreateNew.visibility = View.VISIBLE
            binding.vpgShortVideo.isUserInputEnabled = true
        }
    }
    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
    }
    override fun onPause() {
        super.onPause()
        ExoPlayerManager.pause()
    }

    override fun onResume() {
        super.onResume()
        ExoPlayerManager.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        ExoPlayerManager.release()
    }
}