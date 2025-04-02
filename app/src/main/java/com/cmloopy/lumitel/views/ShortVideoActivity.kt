package com.cmloopy.lumitel.views

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.cmloopy.lumitel.adapter.ShortAdapter
import com.cmloopy.lumitel.data.models.ShortVideo
import com.cmloopy.lumitel.databinding.ActivityShortVideoBinding
import com.cmloopy.lumitel.fragment.bottomsheet.ShortCommentBottomSheet
import com.cmloopy.lumitel.viewmodels.ShortViewModel

@Suppress("DEPRECATION")
class ShortVideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShortVideoBinding
    private val viewModel: ShortViewModel by viewModels()
    private lateinit var adapter: ShortAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShortVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        adapter = ShortAdapter(this, emptyList(),
            { video ->
            showCommentDialog(video)
        },
            { isRotated ->
            rotateVideo(isRotated)
        })

        observeViewModel()

        binding.vpgShortVideo.adapter = adapter

        binding.btnDropBack.setOnClickListener {
            binding.vpgShortVideo.adapter = null
            viewModel.videos.removeObservers(this)
            finish()
        }
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

    private fun showCommentDialog(video: ShortVideo){
        val dialog = ShortCommentBottomSheet()
        dialog.show(supportFragmentManager, "ShortCommentBottomSheet")
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
