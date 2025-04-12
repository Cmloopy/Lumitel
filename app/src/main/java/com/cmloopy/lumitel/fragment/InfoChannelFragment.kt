package com.cmloopy.lumitel.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmloopy.lumitel.viewmodels.InfoChannelViewModel
import com.cmloopy.lumitel.R

class InfoChannelFragment : Fragment() {

    companion object {
        fun newInstance() = InfoChannelFragment()
    }

    private val viewModel: InfoChannelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_info_channel, container, false)
    }
}