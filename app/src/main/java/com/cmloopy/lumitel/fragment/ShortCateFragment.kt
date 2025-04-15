package com.cmloopy.lumitel.fragment

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.GridLayoutManager
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.adapter.ShortCateAdapter
import com.cmloopy.lumitel.databinding.FragmentShortCateBinding
import com.cmloopy.lumitel.viewmodels.VideoCateViewModel
import com.cmloopy.lumitel.views.VideoViewActivity

class ShortCateFragment : Fragment() {
    private lateinit var binding: FragmentShortCateBinding

    private var isLoading = false
    private val viewModel: VideoCateViewModel by viewModels()

    private lateinit var adapter: ShortCateAdapter
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
        binding = FragmentShortCateBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        observeViewModel()
    }
    private fun setupRecyclerViews() {
        adapter = ShortCateAdapter(emptyList()) {idVideo ->
            viewModel.onItemClicked(idVideo)
        }
        binding.recycleViewShortCate.layoutManager = GridLayoutManager(requireContext(),2)
        binding.recycleViewShortCate.adapter = adapter
    }
    private fun observeViewModel() {
        viewModel.videos.observe(viewLifecycleOwner){videos ->
            adapter.updateData(videos)
        }
        binding.recycleViewShortCate.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                binding.recycleViewShortCate.viewTreeObserver.removeOnGlobalLayoutListener(this)
                viewModel.apply {
                    setLoadingS()
                    setLoadingL()
                    setLoading()
                }
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if(isLoading){
                binding.progressBarLoading2.visibility = View.VISIBLE
            }
            else {
                binding.progressBarLoading2.visibility = View.GONE
            }
        }

        viewModel.resultList.observe(viewLifecycleOwner) {list ->
            if(list.isEmpty()) binding.txtEmptyDataShortCate.visibility = View.VISIBLE else binding.txtEmptyDataShortCate.visibility = View.GONE
        }

        viewModel.idVideo.observe(viewLifecycleOwner) {idVideo ->
            val intent = Intent(requireContext(), VideoViewActivity::class.java)
            intent.putExtra("idVideo", idVideo)
            intent.putExtra("idCategory", idCategory)
            intent.putExtra("isFromChannel",false)
            startActivity(intent)
        }
    }

}