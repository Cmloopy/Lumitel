package com.cmloopy.lumitel.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.adapter.VideoCategoryAdapter
import com.cmloopy.lumitel.databinding.FragmentVideoBinding
import com.cmloopy.lumitel.fragment.bottomsheet.BottomSheetMore
import com.cmloopy.lumitel.viewmodels.VideoFragmentViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class VideoFragment : Fragment() {
    private lateinit var _binding: FragmentVideoBinding
    private val binding get() = _binding
    private val viewModel: VideoFragmentViewModel by viewModels()
    private var msisdn: String? = null

    companion object {
        fun newInstance(msisdn: String): VideoFragment {
            val fragment = VideoFragment()
            val args = Bundle()
            args.putString("msisdn", msisdn)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoBinding.inflate(inflater, container,false)

        msisdn = arguments?.getString("msisdn")?:"0"
        viewModel.getCategory(msisdn!!)
        binding.lnTab.visibility = View.GONE

        viewModel.categories.observe(viewLifecycleOwner) {categories ->
            if(categories.isNotEmpty()){
                binding.lnTab.visibility = View.VISIBLE
                binding.progressBarLoadingVideoFragment.visibility = View.GONE

                //Gán adapter cho ViewPager2
                val adapter = VideoCategoryAdapter(this, categories = categories, msisdn = msisdn)
                binding.vpgTabCategory.adapter = adapter
                binding.vpgTabCategory.isUserInputEnabled = false

                //Gán fragment với tab
                TabLayoutMediator(binding.tabCategory, binding.vpgTabCategory) { tab, position ->
                    val customView = LayoutInflater.from(requireContext()).inflate(R.layout.item_tab_video, null)
                    val textView = customView.findViewById<TextView>(R.id.tabText)
                    textView.text = categories[position].cateName
                    tab.customView = customView
                }.attach()

                //Set hiệu ứng khi chọn tab & kéo đến tab
                binding.tabCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                    override fun onTabSelected(p0: TabLayout.Tab?) {
                        val customView = p0?.customView
                        customView?.findViewById<TextView>(R.id.tabText)?.apply {
                            textSize = 25f
                        }
                    }
                    override fun onTabUnselected(p0: TabLayout.Tab?) {
                        val customView = p0?.customView
                        customView?.findViewById<TextView>(R.id.tabText)?.apply {
                            textSize = 15f
                        }
                    }
                    override fun onTabReselected(p0: TabLayout.Tab?) {}
                })

                //Set hiệu ứng cho tab đầu tiên
                binding.tabCategory.post {
                    val firstTab = binding.tabCategory.getTabAt(0)
                    firstTab?.customView?.findViewById<TextView>(R.id.tabText)?.apply {
                        textSize = 25f
                    }
                }
            }
            else {
                Handler(Looper.getMainLooper()).postDelayed({
                    //binding.progressBarLoadingVideoFragment.visibility = View.GONE
                    Toast.makeText(requireContext(),"Kết nối không ổn định!", Toast.LENGTH_SHORT).show()
                }, 1000)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnVideoMore.setOnClickListener {
            val bottomSheet = BottomSheetMore.newInstance(msisdn = msisdn)
            bottomSheet.show(parentFragmentManager, "")
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            reloadData()
        }
    }

    private fun reloadData() {
        msisdn = arguments?.getString("msisdn")?: "0"
        viewModel.getCategory(msisdn!!)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.swipeRefreshLayout.isRefreshing = false
        }, 500)
    }
}