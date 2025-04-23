package com.cmloopy.lumitel.views

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.databinding.ActivityCreateVideoBinding
import com.cmloopy.lumitel.utils.DialogUtils
import com.cmloopy.lumitel.viewmodels.CreateVideoViewModel

class CreateVideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateVideoBinding
    private val viewModel: CreateVideoViewModel by viewModels()
    private var msisdn: String? = null

    private val pickVideoLauncher: ActivityResultLauncher<String> = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            viewModel.prepareVideoFile(this, it)
        }
    }
    private val pickImageLauncher: ActivityResultLauncher<String> = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            viewModel.prepareImageFile(this, it)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityCreateVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        msisdn = intent.getStringExtra("msisdn") ?: "0"
        viewModel.setIdUser(msisdn = msisdn!!)

        obserViewModel()
        binding.btnBackMyChannel.setOnClickListener {
            finish()
        }
        binding.btnChooseFileVideo.setOnClickListener {
            openGalleryToPickVideo()
            viewModel.videoData.observe(this){
                binding.txtNameFile.text = it.first
            }
        }
        binding.btnChooseFileImage.setOnClickListener {
            openGalleryToPickImage()
            viewModel.imageData.observe(this){
                binding.txtNameImageFile.text = it.first
            }
        }
        binding.btnUploadVideo.setOnClickListener {
            val title = binding.edtVideoTitle.text.toString().trim()
            val desc = binding.edtVideoDesc.text.toString().trim()

            if (title.isEmpty() || desc.isEmpty() || title == "" || desc == "") {
                Toast.makeText(this, "Please enter infomation!", Toast.LENGTH_SHORT).show()
            }
            else{
                DialogUtils.showProgressDialog(this) {
                    viewModel.cancelUpload()
                }
                viewModel.setVideoTitle(title)
                viewModel.setVideoDesc(desc)
                viewModel.createVideo(msisdn = msisdn!!)
            }
        }
    }
    private fun obserViewModel() {
        viewModel.nameCates.observe(this){
            val adapter = ArrayAdapter(this, R.layout.item_category_spinner, it)
            binding.spinnerCategory.adapter = adapter

            binding.spinnerCategory.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val selectedName = it[position]
                    viewModel.setCategory(selectedName)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    viewModel.setCategory(it[0])
                }
            })
        }
        viewModel.statusCreate.observe(this) {
            DialogUtils.dismissProgressDialog()
            if(it){
                DialogUtils.showSuccessDialog(this, "Your Video was created successfully!") {finish()}
            }
            else {
                DialogUtils.showFailDialog(this, "Failed to create Video!")
            }
        }
    }
    fun openGalleryToPickVideo() {
        pickVideoLauncher.launch("video/*")
    }
    fun openGalleryToPickImage() {
        pickImageLauncher.launch("image/*")
    }
}