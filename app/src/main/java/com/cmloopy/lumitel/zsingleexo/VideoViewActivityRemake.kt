package com.cmloopy.lumitel.zsingleexo

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cmloopy.lumitel.data.models.video.Video
import com.cmloopy.lumitel.databinding.ActivityVideoViewRemakeBinding
import com.cmloopy.lumitel.manager.ExoPlayerManager
import com.cmloopy.lumitel.utils.DialogUtils
import com.cmloopy.lumitel.views.ChannelActivity
import com.cmloopy.lumitel.views.CreateVideoActivity

@Suppress("DEPRECATION")
@UnstableApi
class VideoViewActivityRemake : AppCompatActivity() {
    private lateinit var binding: ActivityVideoViewRemakeBinding
    private val viewModel: VideoViewModelRemake by viewModels()
    private lateinit var adapter: VideoViewAdapterRemake
    private var idCategory = -1
    private var idVideo = -1
    private var isFromChannel = false
    private var idChannel = -1
    private var isShort = false
    private var msisdn: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoViewRemakeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        idCategory = intent.getIntExtra("idCategory", -1)
        idVideo = intent.getIntExtra("idVideo", -1)
        isFromChannel = intent.getBooleanExtra("isFromChannel", false)
        idChannel = intent.getIntExtra("idChannel", -1)
        isShort = intent.getBooleanExtra("isShort", false)
        msisdn = intent.getStringExtra("msisdn")?:"0"

        viewModel.updateId(idCategory, idVideo, isFromChannel, idChannel, isShort, msisdn!!)
        viewModel.getInfoChannel(msisdn!!)

        adapter = VideoViewAdapterRemake(emptyList(), this, msisdn!!,
            {
                viewModel.rotateVideo()
            },
            { video ->
                showChannel(video)
            }, {
                    isFollow, channelId ->
                followChannel(isFollow, channelId, msisdn!!)
            })
        binding.btnDropBack.setOnClickListener {
            finish()
            ExoPlayerManager.release()
            binding.vpgShortVideo.adapter = null
            viewModel.videos.removeObservers(this)
        }
        binding.btnCreateNew.setOnClickListener {
            viewModel.channel.observe(this){
                when (it.state) {
                    "APPROVED" -> {
                        val intent = Intent(this, CreateVideoActivity::class.java)
                        intent.putExtra("msisdn", msisdn)
                        startActivity(intent)
                    }
                    null -> DialogUtils.channelIsNotExistDialog(this, msisdn = msisdn)
                    else -> {
                        DialogUtils.notiDialog(this)
                    }
                }
            }
        }

        viewModel.videos.observe(this){
            adapter = VideoViewAdapterRemake(it, this, msisdn!!,{
                viewModel.rotateVideo()
            },
                { video ->
                    showChannel(video)
                }, {
                        isFollow, channelId ->
                    followChannel(isFollow, channelId, msisdn!!)
                })
            binding.vpgShortVideo.adapter = adapter
            binding.vpgShortVideo.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.vpgShortVideo.post {
                        val recyclerView = binding.vpgShortVideo.getChildAt(0) as? RecyclerView
                        val holder = recyclerView?.findViewHolderForAdapterPosition(position)
                                as? VideoViewAdapterRemake.VideoViewHolder

                        val video = adapter.getVideo(position) ?: return@post

                        holder?.playerView?.player = ExoPlayerManager.getPlayer(this@VideoViewActivityRemake)
                        ExoPlayerManager.play(this@VideoViewActivityRemake,video.videoMedia)

                        adapter.getVideo(position + 1)?.let {url ->
                            ExoPlayerManager.preloadVideo(url.videoMedia)
                        }
                        adapter.getVideo(position - 1)?.let {url ->
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
    private fun showChannel(video: Video) {
        val intent = Intent(this, ChannelActivity::class.java)
        intent.putExtra("idChannel", video.channelId)
        intent.putExtra("msisdn",msisdn)
        startActivity(intent)
    }
    private fun followChannel(follow: Int, channelId: Int, msisdn: String) {
        viewModel.setStatusFollow(follow)
        viewModel.followChannel(channelId, msisdn)
        viewModel.isFollow.observe(this){
            adapter.updateFollow(it)
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
        binding.vpgShortVideo.adapter = null
        viewModel.videos.removeObservers(this)
        viewModel.isRotate.removeObservers(this)
        ExoPlayerManager.release()
    }
}