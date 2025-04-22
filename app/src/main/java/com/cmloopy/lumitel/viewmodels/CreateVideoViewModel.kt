package com.cmloopy.lumitel.viewmodels

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmloopy.lumitel.data.models.category.Category
import com.cmloopy.lumitel.data.repository.CategoryRepository
import com.cmloopy.lumitel.data.repository.VideoRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException


class CreateVideoViewModel: ViewModel() {
    private val categoryRepository = CategoryRepository()
    private val videoRepository = VideoRepository()

    private val _categories = MutableLiveData<List<Category>>()
    private val _nameCate = MutableLiveData<List<String>>()
    private val _chooseFileCate = MutableLiveData<String>()
    private val _videoTitle = MutableLiveData<String>()
    private val _videoDesc = MutableLiveData<String>()
    private val _videoData = MutableLiveData<Pair<String, MultipartBody.Part>>()
    private val _imageData = MutableLiveData<Pair<String, MultipartBody.Part>>()

    private val _statusCreate = MutableLiveData<Boolean>()

    //Data create video
    private val _videoPath = MutableLiveData<String>()
    private val _imagePath = MutableLiveData<String>()
    val nameCates : LiveData<List<String>> get() = _nameCate
    val videoData: LiveData<Pair<String, MultipartBody.Part>> get() = _videoData
    val imageData: LiveData<Pair<String, MultipartBody.Part>> get() = _imageData
    val statusCreate : LiveData<Boolean> get() = _statusCreate
    fun setIdUser(msisdn: String){
        getAllRepo(msisdn)
    }
    fun setVideoTitle(s: String){
        _videoTitle.value = s
    }
    fun setVideoDesc(s: String){
        _videoDesc.value = s
    }
    fun setCategory(category: String){
        _chooseFileCate.value = category
    }
    private fun getAllRepo(msisdn: String) {
        viewModelScope.launch {
            try {
                val result = categoryRepository.getAllCategory(msisdn)
                _categories.value = result
                val cateNames = result.drop(3).map { it.cateName }
                _nameCate.value = cateNames
            } catch (e:Exception){
                e.printStackTrace()
                _categories.value = emptyList()
            }
        }
    }
    fun prepareVideoFile(context: Context, uri: Uri) {
        val fileName = getFileNameFromUri(context, uri)
        val file = createFileFromUri(context, uri)

        val mimeType = context.contentResolver.getType(uri) ?: "video/*"
        val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
        val multipart = MultipartBody.Part.createFormData("uFile", fileName, requestFile)

        _videoData.value = Pair(fileName, multipart)
    }
    fun prepareImageFile(context: Context, uri: Uri) {
        val fileName = getFileNameFromUri(context, uri)
        val file = createFileFromUri(context, uri)

        val mimeType = context.contentResolver.getType(uri) ?: "image/*"
        val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
        val multipart = MultipartBody.Part.createFormData("uFile", fileName, requestFile)

        _imageData.value = Pair(fileName, multipart)
    }
    private fun getFileNameFromUri(context: Context, uri: Uri): String {
        var name = "NaN"
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index >= 0) name = it.getString(index)
            }
        }
        return name
    }
    private fun createFileFromUri(context: Context, uri: Uri): File {
        val fileName = getFileNameFromUri(context, uri)
        val extension = fileName.substringAfterLast('.', "NaN")
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IOException("Can't open input")
        val tempFile = File.createTempFile("upload_", ".$extension", context.cacheDir)
        tempFile.outputStream().use { output ->
            inputStream.copyTo(output)
        }
        return tempFile
    }
    private suspend fun uploadVideo(msisdn: String): Boolean {
        return try {
            val result = videoRepository.uploadVideo(msisdn, _videoData.value!!.first, _videoData.value!!.second)
            if (result.code == 200) {
                _videoPath.value = result.mediaPath
                Log.e("uploadVideoViewodel", "Done")
                true
            } else {
                _videoPath.value = ""
                Log.e("uploadVideoViewodel", "Failed")
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("uploadVideoViewodel", "Failed exception")
            false
        }
    }

    private suspend fun uploadImage(msisdn: String): Boolean {
        return try {
            val result = videoRepository.uploadImage(msisdn, _imageData.value!!.first, _imageData.value!!.second)
            if (result.code == 200) {
                _imagePath.value = result.mediaPath
                Log.e("uploadImageViewodel", "Done")
                true
            } else {
                _imagePath.value = ""
                Log.e("uploadImageViewodel", "Fail")
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _imagePath.value = ""
            Log.e("uploadImageViewodel", "Fail exception")
            false
        }
    }
    fun createVideo(msisdn: String) {
        viewModelScope.launch {
            // Dùng async để chạy song song
            val videoUploadDeferred = async { uploadVideo(msisdn) }
            val imageUploadDeferred = async { uploadImage(msisdn) }

            val videoUploadSuccess = videoUploadDeferred.await()
            val imageUploadSuccess = imageUploadDeferred.await()

            if (videoUploadSuccess && imageUploadSuccess) {
                val idCate = _categories.value?.find { it.cateName == _chooseFileCate.value }?.id ?: 2
                Log.e("Cate", "$idCate")
                try {
                    val result = videoRepository.createVideo(
                        msisdn = msisdn,
                        categoryId = idCate,
                        videoTitle = _videoTitle.value.toString(),
                        videoDesc = _videoDesc.value.toString(),
                        imageUrl = _imagePath.value.toString(),
                        videoUrl = _videoPath.value.toString()
                    )
                    _statusCreate.value = result.code == 200
                } catch (e: Exception) {
                    e.printStackTrace()
                    _statusCreate.value = false
                    Log.e("err", "lỗi upload")
                }
            } else {
                _statusCreate.value = false
                Log.e("err", "lỗi upload")
            }
        }
    }

}