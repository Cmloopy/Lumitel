package com.cmloopy.lumitel.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.adapter.VideoCategoryAdapter
import com.cmloopy.lumitel.databinding.FragmentVideoBinding
import com.cmloopy.lumitel.viewmodels.VideoFragmentViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class VideoFragment : Fragment() {
    private lateinit var _binding: FragmentVideoBinding
    private val binding get() = _binding
    private val viewModel: VideoFragmentViewModel by viewModels()

    companion object {
        fun newInstance() = VideoFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoBinding.inflate(inflater, container,false)

        //Gán adapter cho ViewPager2
        val adapter = VideoCategoryAdapter(this)
        binding.vpgTabCategory.adapter = adapter

        binding.vpgTabCategory.isUserInputEnabled = false

        //Gán fragment với tab
        val tabTitles = listOf("Hot", "Funny", "News", "Motivasaun")
        TabLayoutMediator(binding.tabCategory, binding.vpgTabCategory) { tab, position ->
            val customView = LayoutInflater.from(requireContext()).inflate(R.layout.item_tab_video, null)
            val textView = customView.findViewById<TextView>(R.id.tabText)
            textView.text = tabTitles[position]
            tab.customView = customView
        }.attach()

        //Set hiệu ứng khi chọn tab & kéo đến tab
        binding.tabCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(p0: TabLayout.Tab?) {
                val customView = p0?.customView
                customView?.findViewById<TextView>(R.id.tabText)?.apply {
                    textSize = 25f
                    setTextColor(ContextCompat.getColor(context, R.color.blue_3))
                }
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {
                val customView = p0?.customView
                customView?.findViewById<TextView>(R.id.tabText)?.apply {
                    textSize = 15f
                    setTextColor(ContextCompat.getColor(context, R.color.blue_4))
                }
            }
            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })

        //Set hiệu ứng cho tab đầu tiên
        binding.tabCategory.post {
            val firstTab = binding.tabCategory.getTabAt(0)
            firstTab?.customView?.findViewById<TextView>(R.id.tabText)?.apply {
                textSize = 25f
                setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_3))
            }
        }
        return binding.root
    }
}