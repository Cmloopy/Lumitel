package com.cmloopy.lumitel.fragment

import android.content.Intent
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
import com.cmloopy.lumitel.views.VideoViewActivity

class VideoChannelFragment : Fragment() {
    private lateinit var binding: FragmentVideoChannelBinding

    private val viewModel: VideoChannelViewModel by viewModels()
    private var channelId: Int = -1
    private var msisdn: String? = null

    companion object{
        fun newInstance(channelId: Int, msisdn: String?): VideoChannelFragment {
            val fragment = VideoChannelFragment()
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
        binding = FragmentVideoChannelBinding.inflate(inflater,container,false)

        channelId = arguments?.getInt("channelId", -1) ?: -1
        msisdn = arguments?.getString("msisdn")?: "0"
        viewModel.setChannelId(channelId = channelId, msisdn!!)

        viewModel.videol.observe(viewLifecycleOwner) { videol ->
            val adapter = VideoChannelAdapter(videol) {idVideo ->
                val intent = Intent(requireContext(),VideoViewActivity::class.java)
                intent.putExtra("idVideo", idVideo)
                intent.putExtra("isFromChannel", true)
                intent.putExtra("isShort", false)
                intent.putExtra("idChannel", channelId)
                startActivity(intent)
            }
            binding.recycleViewVideoChannel.layoutManager = LinearLayoutManager(requireContext())
            binding.recycleViewVideoChannel.adapter = adapter

            if(videol.isEmpty()) binding.txtEmptyDataVideoChannel.visibility = View.VISIBLE else binding.txtEmptyDataVideoChannel.visibility = View.GONE
        }

        return binding.root
    }
}