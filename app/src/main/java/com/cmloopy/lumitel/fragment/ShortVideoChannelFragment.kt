package com.cmloopy.lumitel.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.adapter.ShortChannelAdapter
import com.cmloopy.lumitel.databinding.FragmentShortVideoChannelBinding
import com.cmloopy.lumitel.viewmodels.ShortVideoChannelViewModel

class ShortVideoChannelFragment : Fragment() {
    private lateinit var binding: FragmentShortVideoChannelBinding
    private var channelId: Int = -1

    private val viewModel: ShortVideoChannelViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShortVideoChannelBinding.inflate(inflater, container, false)
        channelId = arguments?.getInt("channelId", -1) ?: -1

        viewModel.setChannelId(channelId = channelId)

        viewModel.videos.observe(viewLifecycleOwner){videos ->
            val adapter = ShortChannelAdapter(videos)
            binding.recyclerViewShortChannel.layoutManager = GridLayoutManager(requireContext(),3)
            binding.recyclerViewShortChannel.adapter = adapter
        }

        return binding.root
    }
}