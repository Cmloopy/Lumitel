package com.cmloopy.lumitel.fragment.bottomsheet

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.databinding.BottomSheetMoreBinding
import com.cmloopy.lumitel.utils.DialogUtils
import com.cmloopy.lumitel.viewmodels.BottomSheetMoreViewModel
import com.cmloopy.lumitel.views.ChannelActivity
import com.cmloopy.lumitel.views.FollowChannelActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetMore: BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetMoreBinding
    private var msisdn: String? = null
    private val viewModel: BottomSheetMoreViewModel by viewModels()
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

        viewModel.setIdUser(msisdn!!)

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
            viewModel.existChannel.observe(viewLifecycleOwner){
                Log.e("err", "$it")
                if(it){
                    val intent = Intent(requireContext(), ChannelActivity::class.java)
                    intent.putExtra("msisdn", msisdn)
                    startActivity(intent)
                    dismiss()
                } else {
                    DialogUtils.channelIsNotExistDialog(requireContext(),msisdn)
                    dismiss()
                }
            }
        }
        binding.btnShowMyLibrary.setOnClickListener {

        }
        binding.btnShowMyFollowing.setOnClickListener {
            viewModel.existChannel.observe(viewLifecycleOwner){
                Log.e("err", "$it")
                if(it){
                    val intent = Intent(requireContext(), FollowChannelActivity::class.java)
                    intent.putExtra("msisdn", msisdn)
                    startActivity(intent)
                    dismiss()
                } else {
                    DialogUtils.channelIsNotExistDialog(requireContext(),msisdn)
                    dismiss()
                }
            }
        }
        binding.btnCancelBottomSheetMore.setOnClickListener {
            dismiss()
        }
    }
}