package com.cmloopy.lumitel.views

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.cmloopy.lumitel.adapter.ShortAdapter
import com.cmloopy.lumitel.data.models.video.Video
import com.cmloopy.lumitel.databinding.ActivityVideoViewBinding
import com.cmloopy.lumitel.fragment.bottomsheet.BottomSheetComment
import com.cmloopy.lumitel.viewmodels.VideoViewModel

//XỬ LÝ LIKE SHARE

@Suppress("DEPRECATION")
class VideoViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoViewBinding
    private val viewModel: VideoViewModel by viewModels()
    private lateinit var adapter: ShortAdapter
    private var idCategory = -1
    private var idVideo = -1
    private var isFromChannel = false
    private var idChannel = -1
    private var isShort = false
    private var msisdn: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        idCategory = intent.getIntExtra("idCategory", -1)
        idVideo = intent.getIntExtra("idVideo", -1)
        isFromChannel = intent.getBooleanExtra("isFromChannel", false)
        idChannel = intent.getIntExtra("idChannel", -1)
        isShort = intent.getBooleanExtra("isShort", false)
        msisdn = intent.getStringExtra("msisdn")?:"0"

        viewModel.updateId(idCategory, idVideo, isFromChannel, idChannel, isShort, msisdn!!)

        adapter = ShortAdapter(this, emptyList(),
            { video ->
            showCommentDialog(video)
        },
            { isRotated ->
            rotateVideo(isRotated)
        },
            { video ->
                showChannel(video)
            })

        observeViewModel()

        binding.vpgShortVideo.adapter = adapter

        binding.btnDropBack.setOnClickListener {
            finish()
            binding.vpgShortVideo.adapter = null
            viewModel.videos.removeObservers(this)
        }
        binding.btnCreateNew.setOnClickListener {

        }
    }

    private fun showChannel(video: Video) {
        val intent = Intent(this, ChannelActivity::class.java)
        intent.putExtra("idChannel", video.channelId)
        intent.putExtra("msisdn",msisdn)
        startActivity(intent)
    }

    private fun rotateVideo(b: Boolean) {
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

    private fun observeViewModel() {
        viewModel.videos.observe(this) { videos ->
            adapter.updateData(videos)
        }
    }

    private fun showCommentDialog(video: Video){
        //msisdn & idvideo?
        val dialog = BottomSheetComment.newInstance(video.id, msisdn)
        dialog.show(supportFragmentManager, "BottomSheetComment")
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.releaseVideo()
    }

    override fun onPause() {
        super.onPause()
        adapter.pauseVideo()
    }

    override fun onResume() {
        super.onResume()
        adapter.resumeVideo()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if(requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            adapter.backToPortrait()
        }
        else {
            binding.vpgShortVideo.adapter = null
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

}
