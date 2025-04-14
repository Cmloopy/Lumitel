package com.cmloopy.lumitel.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmloopy.lumitel.viewmodels.InfoChannelViewModel
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.databinding.FragmentInfoChannelBinding

class InfoChannelFragment : Fragment() {
    private lateinit var binding: FragmentInfoChannelBinding

    private val viewModel: InfoChannelViewModel by viewModels()
    private var channelId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoChannelBinding.inflate(inflater, container, false)

        channelId = arguments?.getInt("channelId", -1) ?: -1

        return binding.root
    }
}