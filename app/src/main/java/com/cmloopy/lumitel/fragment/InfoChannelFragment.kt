package com.cmloopy.lumitel.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmloopy.lumitel.databinding.FragmentInfoChannelBinding
import com.cmloopy.lumitel.viewmodels.ChannelViewModel

class InfoChannelFragment : Fragment() {
    private lateinit var binding: FragmentInfoChannelBinding

    private val viewModel: ChannelViewModel by viewModels()
    private var channelId: Int = -1
    private var msisdn: String? = null

    companion object{
        fun newInstance(channelId: Int, msisdn: String?): InfoChannelFragment {
            val fragment = InfoChannelFragment()
            fragment.arguments = Bundle().apply {
                putInt("channelId", channelId)
                putString("msisdn", msisdn)
            }
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoChannelBinding.inflate(inflater, container, false)

        channelId = arguments?.getInt("channelId", -1) ?: -1
        msisdn = arguments?.getString("msisdn") ?: "0"
        viewModel.setChannelId(channelId = channelId, msisdn = msisdn!!)

        return binding.root
    }
}