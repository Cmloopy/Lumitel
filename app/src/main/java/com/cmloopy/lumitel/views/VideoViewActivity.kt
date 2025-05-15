package com.cmloopy.lumitel.views

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.cmloopy.lumitel.adapter.VideoViewAdapter
import com.cmloopy.lumitel.viewmodels.VideoViewModel
import com.cmloopy.lumitel.data.models.video.Video
import com.cmloopy.lumitel.databinding.ActivityVideoViewRemakeBinding
import com.cmloopy.lumitel.fragment.bottomsheet.BottomSheetComment
import com.cmloopy.lumitel.utils.DialogUtils

@Suppress("DEPRECATION")
class VideoViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoViewRemakeBinding
    private val viewModel: VideoViewModel by viewModels()
    private lateinit var adapter : VideoViewAdapter
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

        adapter = VideoViewAdapter(emptyList(), this, msisdn!!,
        { video ->
            showCommentDialog(video)
        },
        { isRotated ->
            rotateVideo(isRotated)
        },
        { video ->
            showChannel(video)
        }, {
            isFollow, channelId ->
            followChannel(isFollow, channelId, msisdn!!)
            })

        obserViewModel()

        binding.btnDropBackRemake.setOnClickListener {
            finish()
            adapter.releaseAll()
            binding.vpgShortVideoRemake.adapter = null
            viewModel.videos.removeObservers(this)
        }
        binding.btnCreateNewRemake.setOnClickListener {
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
    }

    private fun followChannel(follow: Int, channelId: Int, msisdn: String) {
        viewModel.setStatusFollow(follow)
        viewModel.followChannel(channelId, msisdn)
        viewModel.isFollow.observe(this){
            adapter.updateFollow(it)
        }
    }

    private fun obserViewModel() {
        viewModel.videos.observe(this) { videos ->
            adapter = VideoViewAdapter(videos, this, msisdn!!,
                { video ->
                showCommentDialog(video)
                },
                { isRotated ->
                    rotateVideo(isRotated)
                },
                { video ->
                    showChannel(video)
                },{
                        isFollow, channelId ->
                    followChannel(isFollow, channelId, msisdn!!)
                })
            binding.vpgShortVideoRemake.adapter = adapter
        }
    }
    private fun showChannel(video: Video) {
        val intent = Intent(this, ChannelActivity::class.java)
        intent.putExtra("idChannel", video.channelId)
        intent.putExtra("msisdn",msisdn)
        startActivity(intent)
    }
    private fun showCommentDialog(video: Video){
        val dialog = BottomSheetComment.newInstance(video.id, msisdn)
        dialog.show(supportFragmentManager, "BottomSheetComment")
    }
    private fun rotateVideo(b: Boolean) {
        if(b){
            hideSystemUI()
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            binding.btnDropBackRemake.visibility = View.GONE
            binding.btnCreateNewRemake.visibility = View.GONE
            binding.vpgShortVideoRemake.isUserInputEnabled = false
        }
        else{
            showSystemUI()
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            binding.btnDropBackRemake.visibility = View.VISIBLE
            binding.btnCreateNewRemake.visibility = View.VISIBLE
            binding.vpgShortVideoRemake.isUserInputEnabled = true
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if(requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            adapter.backToPortrait()
        }
        else {
            binding.vpgShortVideoRemake.adapter = null
            viewModel.videos.removeObservers(this)
            super.onBackPressed()
            finish()
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
        adapter.stopVideo()
    }
    override fun onResume() {
        super.onResume()
        adapter.resumeVideo()
    }
}