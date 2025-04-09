package com.cmloopy.lumitel.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmloopy.lumitel.viewmodels.AllVideoViewModel
import com.cmloopy.lumitel.adapter.LengthVideoAdapter
import com.cmloopy.lumitel.adapter.ShortVideoAdapter
import com.cmloopy.lumitel.databinding.FragmentAllVideoBinding

class AllVideoFragment : Fragment() {
    private lateinit var binding: FragmentAllVideoBinding
    private val viewModel: AllVideoViewModel by viewModels()
    private var isLoaded = false

    private lateinit var shortAdapter: ShortVideoAdapter
    private lateinit var lengthAdapter: LengthVideoAdapter

    override fun onResume() {
        super.onResume()
        if (!isLoaded && isVisible) {
            val idCategory = arguments?.getInt("idCategory", -1) ?: -1
            viewModel.setCategory(idCategory)
            isLoaded = true
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllVideoBinding.inflate(inflater, container, false)
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
    }
}
