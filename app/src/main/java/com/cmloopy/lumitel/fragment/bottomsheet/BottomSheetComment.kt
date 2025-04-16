package com.cmloopy.lumitel.fragment.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.adapter.ShortCommentAdapter
import com.cmloopy.lumitel.data.models.video.Video
import com.cmloopy.lumitel.databinding.BottomSheetCommentBinding
import com.cmloopy.lumitel.viewmodels.VideoViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetComment: BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetCommentBinding
    //private lateinit var video: ShortVideo
    private lateinit var shortCommentAdapter: ShortCommentAdapter
    private val viewModel: VideoViewModel by viewModels()

    companion object {
        fun newInstance(video: Video) { }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetCommentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<FrameLayout>(
                com.google.android.material.R.id.design_bottom_sheet
            )
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                it.background = ContextCompat.getDrawable(requireContext(), R.drawable.bottom_sheet_short_cmt_bg)
            }
        }

        return dialog
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