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

    companion object {
        fun newInstance() = VideoChannelFragment()
    }

    private val viewModel: VideoChannelViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoChannelBinding.inflate(inflater,container,false)

        viewModel.videol.observe(viewLifecycleOwner) { videol ->
            val adapter = VideoChannelAdapter(videol)
            binding.recycleViewVideoChannel.layoutManager = LinearLayoutManager(requireContext())
            binding.recycleViewVideoChannel.adapter = adapter
        }

        return binding.root
    }
}