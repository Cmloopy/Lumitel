package com.cmloopy.lumitel.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmloopy.lumitel.viewmodels.HotVideoViewModel
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.adapter.HotVideoAdapter
import com.cmloopy.lumitel.adapter.ShortVideoAdapter
import com.cmloopy.lumitel.databinding.FragmentHotVideoBinding

class HotVideoFragment : Fragment() {
    private lateinit var binding: FragmentHotVideoBinding

    companion object {
        fun newInstance() = HotVideoFragment()
    }
    private val viewModel: HotVideoViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHotVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Short Video
        val shortAdapter = ShortVideoAdapter(emptyList())
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewShortVideo.layoutManager = layoutManager
        binding.recyclerViewShortVideo.adapter = shortAdapter
        binding.recyclerViewShortVideo.isNestedScrollingEnabled = false

        viewModel.videos.observe(viewLifecycleOwner, Observer { videos ->
            shortAdapter.updateData(videos)
        })
        //Length Video
        val lengthAdapter = HotVideoAdapter(emptyList())
        binding.recyclerViewHotVideo.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewHotVideo.adapter = lengthAdapter

        viewModel.videol.observe(viewLifecycleOwner, Observer {videos ->
            lengthAdapter.updateData(videos)
        })
    }
}