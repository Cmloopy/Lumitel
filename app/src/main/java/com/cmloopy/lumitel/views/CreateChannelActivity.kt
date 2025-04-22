package com.cmloopy.lumitel.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.cmloopy.lumitel.databinding.ActivityCreateChannelBinding
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
            viewModel.imageData.observe(this){
                binding.txtNameImageChannel.text = it.first
            }
        }
        binding.btnCreateChannel.setOnClickListener {
            viewModel.setChannelName(binding.edtChannelName.text.toString())
            viewModel.setChannelDesc(binding.edtChannelDesc.text.toString())
            viewModel.createChannel(msisdn = msisdn.toString())
            viewModel.statusCreateChannel.observe(this){
                showSuccessDialog(it)
            }
        }
        binding.btnBackHomeVideo.setOnClickListener {
            finish()
        }
    }
    fun openGalleryToPickImage() {
        pickImageLauncher.launch("image/*")
    }
    private fun showSuccessDialog(b: Boolean) {
        var title = ""
        title = if(b) "Your Channel was created successfully!"
        else "Your Channel was created error"
        AlertDialog.Builder(this)
            .setTitle("Create Channel")
            .setMessage(title)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .show()
    }
}