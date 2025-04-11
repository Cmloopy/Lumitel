package com.cmloopy.lumitel.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.viewmodels.ShortVideoChannelViewModel

class ShortVideoChannelFragment : Fragment() {

    companion object {
        fun newInstance() = ShortVideoChannelFragment()
    }

    private val viewModel: ShortVideoChannelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_short_video_channel, container, false)
    }
}