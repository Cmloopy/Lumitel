package com.cmloopy.lumitel.fragment.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.databinding.BottomSheetMoreBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetMore: BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetMoreBinding
    private var msisdn: String? = null
    companion object {
        fun newInstance(msisdn: String?): BottomSheetMore {
            val fragment = BottomSheetMore()
            val args = Bundle()
            args.putString("msisdn", msisdn)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetMoreBinding.inflate(inflater,container,false)
        msisdn = arguments?.getString("msisdn")

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
        binding.btnShowMyChannel.setOnClickListener {
            Toast.makeText(requireContext(), "Show Channel or Create if it nos exist", Toast.LENGTH_SHORT).show()
        }
        binding.btnShowMyLibrary.setOnClickListener {
            Toast.makeText(requireContext(), "Show Library or Create if it nos exist", Toast.LENGTH_SHORT).show()
        }
        binding.btnShowMyFollowing.setOnClickListener {
            Toast.makeText(requireContext(), "Show Following or Create if it nos exist", Toast.LENGTH_SHORT).show()
        }
        binding.btnCancelBottomSheetMore.setOnClickListener {
            dismiss()
        }
    }
}