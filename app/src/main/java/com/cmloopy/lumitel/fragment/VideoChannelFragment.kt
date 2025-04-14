package com.cmloopy.lumitel.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.adapter.VideoChannelAdapter
import com.cmloopy.lumitel.databinding.FragmentVideoChannelBinding
import com.cmloopy.lumitel.viewmodels.VideoChannelViewModel

class VideoChannelFragment : Fragment() {
    private lateinit var binding: FragmentVideoChannelBinding

    private val viewModel: VideoChannelViewModel by viewModels()
    private var channelId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoChannelBinding.inflate(inflater,container,false)

        channelId = arguments?.getInt("channelId", -1) ?: -1
        viewModel.setChannelId(channelId = channelId)

        viewModel.videol.observe(viewLifecycleOwner) { videol ->
            val adapter = VideoChannelAdapter(videol)
            binding.recycleViewVideoChannel.layoutManager = LinearLayoutManager(requireContext())
            binding.recycleViewVideoChannel.adapter = adapter
        }

        return binding.root
    }
}