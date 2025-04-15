package com.cmloopy.lumitel.fragment

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmloopy.lumitel.viewmodels.VideoCateViewModel
import com.cmloopy.lumitel.adapter.LengthVideoAdapter
import com.cmloopy.lumitel.adapter.ShortVideoAdapter
import com.cmloopy.lumitel.databinding.FragmentVideoCateBinding
import com.cmloopy.lumitel.views.VideoViewActivity

class VideoCateFragment : Fragment() {
    private lateinit var binding: FragmentVideoCateBinding
    private val viewModel: VideoCateViewModel by viewModels()

    private lateinit var shortAdapter: ShortVideoAdapter
    private lateinit var lengthAdapter: LengthVideoAdapter
    private var isLoading = false

    private var idCategory: Int = -1

    override fun onResume() {
        super.onResume()
        if (!isLoading && isVisible) {
            idCategory = arguments?.getInt("idCategory", -1) ?: -1
            viewModel.setCategory(idCategory)
            isLoading = true
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoCateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.materialTextView.visibility = View.GONE
        binding.materialTextView2.visibility = View.GONE
        setupRecyclerViews()
        observeViewModel()
    }

    private fun setupRecyclerViews() {
        // Short Video
        shortAdapter = ShortVideoAdapter(emptyList()) {idVideo ->
            viewModel.onItemClicked(idVideo)
        }
        binding.recyclerViewShortVideo.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = shortAdapter
        }
        // Length Video
        lengthAdapter = LengthVideoAdapter(emptyList()) {idVideo ->
            viewModel.onItemClicked(idVideo)
        }
        binding.recyclerViewHotVideo.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = lengthAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.videos.observe(viewLifecycleOwner) { videos ->
            shortAdapter.updateData(videos)
            binding.recyclerViewShortVideo.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener{
                override fun onGlobalLayout() {
                    binding.recyclerViewShortVideo.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    viewModel.apply {
                        setLoadingS()
                        setLoading()
                    }
                }
            })
        }

        viewModel.videol.observe(viewLifecycleOwner) { videos ->
            lengthAdapter.updateData(videos)
            binding.recyclerViewHotVideo.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener{
                override fun onGlobalLayout() {
                    binding.recyclerViewHotVideo.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    viewModel.apply {
                        setLoadingL()
                        setLoading()
                    }
                }
            })
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if(isLoading){
                binding.progressBarLoading.visibility = View.VISIBLE
                binding.materialTextView.visibility = View.GONE
                binding.materialTextView2.visibility = View.GONE
            }
            else {
                binding.progressBarLoading.visibility = View.GONE
                if(viewModel.isEmptyS.value!!) binding.materialTextView.visibility = View.GONE
                    else binding.materialTextView.visibility = View.VISIBLE
                if(viewModel.isEmptyL.value!!) binding.materialTextView2.visibility = View.GONE
                    else binding.materialTextView2.visibility = View.VISIBLE
            }
        }
        viewModel.resultList.observe(viewLifecycleOwner) {list ->
            if(list.isEmpty()) binding.txtEmptyDataVideoCate.visibility = View.VISIBLE else binding.txtEmptyDataVideoCate.visibility = View.GONE
        }

        viewModel.idVideo.observe(viewLifecycleOwner) {idVideo ->
            val intent = Intent(requireContext(), VideoViewActivity::class.java)
            intent.putExtra("idVideo", idVideo)
            intent.putExtra("idCategory", idCategory)
            intent.putExtra("isFromChannel", false)
            startActivity(intent)
        }
    }
}
