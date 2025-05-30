package com.cmloopy.lumitel.fragment

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.cmloopy.lumitel.adapter.ShortChannelAdapter
import com.cmloopy.lumitel.databinding.FragmentShortVideoChannelBinding
import com.cmloopy.lumitel.viewmodels.ShortVideoChannelViewModel
import com.cmloopy.lumitel.views.VideoViewActivity

class ShortVideoChannelFragment : Fragment() {
    private lateinit var binding: FragmentShortVideoChannelBinding
    private var channelId: Int = -1
    private var msisdn: String? = null

    private val viewModel: ShortVideoChannelViewModel by viewModels()

    companion object{
        fun newInstance(channelId: Int, msisdn: String?): ShortVideoChannelFragment {
            val fragment = ShortVideoChannelFragment()
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
        binding = FragmentShortVideoChannelBinding.inflate(inflater, container, false)
        channelId = arguments?.getInt("channelId", -1) ?: -1
        msisdn = arguments?.getString("msisdn")?: "0"

        viewModel.setChannelId(channelId = channelId, msisdn = msisdn!!)

        viewModel.videos.observe(viewLifecycleOwner){videos ->
            val adapter = ShortChannelAdapter(videos) {idVideo ->
                val intent = Intent(requireContext(),VideoViewActivity::class.java)
                intent.putExtra("idVideo",idVideo)
                intent.putExtra("idChannel",channelId)
                intent.putExtra("isFromChannel", true)
                intent.putExtra("isShort", true)
                intent.putExtra("msisdn",msisdn)
                startActivity(intent)
            }
            binding.recyclerViewShortChannel.layoutManager = GridLayoutManager(requireContext(),3)
            binding.recyclerViewShortChannel.adapter = adapter

            if(videos.isEmpty()) binding.txtEmptyDataShortChannel.visibility = View.VISIBLE
            else binding.txtEmptyDataShortChannel.visibility = View.GONE
        }

        return binding.root
    }
}