package com.cmloopy.lumitel.fragment.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmloopy.lumitel.adapter.ShortCommentAdapter
import com.cmloopy.lumitel.data.models.Video
import com.cmloopy.lumitel.databinding.BottomSheetCommentBinding
import com.cmloopy.lumitel.viewmodels.ShortViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetComment: BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetCommentBinding
    //private lateinit var video: ShortVideo
    private lateinit var shortCommentAdapter: ShortCommentAdapter
    private val viewModel: ShortViewModel by viewModels()

    companion object {
        fun newInstance(video: Video) { }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shortCommentAdapter = ShortCommentAdapter(emptyList())
        binding.recyclerViewShortComment.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewShortComment.adapter = shortCommentAdapter

        val dividerItemDecoration = DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL)
        binding.recyclerViewShortComment.addItemDecoration(dividerItemDecoration)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.comments.observe(viewLifecycleOwner) {comments ->
            shortCommentAdapter.updateComment(comments)
        }
    }
}