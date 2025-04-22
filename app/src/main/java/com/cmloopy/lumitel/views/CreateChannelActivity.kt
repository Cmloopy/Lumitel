package com.cmloopy.lumitel.views

import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.cmloopy.lumitel.databinding.ActivityCreateChannelBinding
import com.cmloopy.lumitel.utils.DialogUtils
import com.cmloopy.lumitel.viewmodels.CreateChannelViewmodel

class CreateChannelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateChannelBinding
    private val viewModel: CreateChannelViewmodel by viewModels()
    private var msisdn: String? = null

    private val pickImageLauncher: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            viewModel.prepareImageFile(this, it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateChannelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        msisdn = intent.getStringExtra("msisdn") ?: "0"

        binding.btnChooseFileImageChannel.setOnClickListener {
            openGalleryToPickImage()
            viewModel.imageData.observe(this) {
                binding.txtNameImageChannel.text = it.first
            }
        }

        binding.btnCreateChannel.setOnClickListener {
            viewModel.setChannelName(binding.edtChannelName.text.toString())
            viewModel.setChannelDesc(binding.edtChannelDesc.text.toString())
            viewModel.createChannel(msisdn = msisdn.toString())
            DialogUtils.showProgressDialog(this) {}

            viewModel.statusCreateChannel.observe(this) {
                DialogUtils.dismissProgressDialog()
                if (it) {
                    DialogUtils.showSuccessDialog(this, "Your Channel was created successfully!") { finish() }
                } else {
                    DialogUtils.showFailDialog(this, "Created error")
                }
            }
        }

        binding.btnBackHomeVideo.setOnClickListener {
            finish()
        }
    }

    private fun openGalleryToPickImage() {
        pickImageLauncher.launch("image/*")
    }
}
