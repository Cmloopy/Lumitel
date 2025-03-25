package com.cmloopy.lumitel.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmloopy.lumitel.viewmodels.HotVideoViewModel
import com.cmloopy.lumitel.adapter.LengthVideoAdapter
import com.cmloopy.lumitel.adapter.ShortVideoAdapter
import com.cmloopy.lumitel.databinding.FragmentHotVideoBinding

class HotVideoFragment : Fragment() {
    private lateinit var binding: FragmentHotVideoBinding
    private val viewModel: HotVideoViewModel by viewModels()

    private lateinit var shortAdapter: ShortVideoAdapter
    private lateinit var lengthAdapter: LengthVideoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHotVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        observeViewModel()
    }

    private fun setupRecyclerViews() {
        // Short Video
        shortAdapter = ShortVideoAdapter(emptyList())
        binding.recyclerViewShortVideo.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = shortAdapter
        }

        // Length Video
        lengthAdapter = LengthVideoAdapter(emptyList())
        binding.recyclerViewHotVideo.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = lengthAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.videos.observe(viewLifecycleOwner) { videos ->
            shortAdapter.updateData(videos)
        }

        viewModel.videol.observe(viewLifecycleOwner) { videos ->
            lengthAdapter.updateData(videos)
        }
    }
}
