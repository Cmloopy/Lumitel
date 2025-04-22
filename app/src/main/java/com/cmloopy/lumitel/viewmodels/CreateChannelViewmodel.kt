package com.cmloopy.lumitel.viewmodels

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmloopy.lumitel.data.repository.ChannelRepository
import com.cmloopy.lumitel.data.repository.VideoRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException

class CreateChannelViewmodel: ViewModel() {
    private val channelRepository = ChannelRepository()
    private val videoRepository = VideoRepository()

    private val _channelName = MutableLiveData<String>()
    private val _channelDesc = MutableLiveData<String>()
    private val _channelAvatar = MutableLiveData<String>()
    private val _imageData = MutableLiveData<Pair<String, MultipartBody.Part>>()

    private val _statusCreateChannel = MutableLiveData<Boolean>()

    val imageData : LiveData<Pair<String, MultipartBody.Part>> get() = _imageData
    val statusCreateChannel : LiveData<Boolean> get() = _statusCreateChannel

    fun setChannelName(s:String){
        _channelName.value = s
    }
    fun setChannelDesc(s:String){
        _channelDesc.value = s
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
    private suspend fun uploadImage(msisdn: String): Boolean {
        return try {
            val result = videoRepository.uploadImage(msisdn, _imageData.value!!.first, _imageData.value!!.second)
            if (result.code == 200) {
                _channelAvatar.value = result.mediaPath
                Log.e("uploadImageViemodel", "Done")
                true
            } else {
                _channelAvatar.value = ""
                Log.e("uploadImageViemodel", "Fail")
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _channelAvatar.value = ""
            Log.e("uploadImageViemodel", "Fail exception")
            false
        }
    }
    fun createChannel(msisdn: String){
        viewModelScope.launch {
            val imageUploadDeferred = async { uploadImage(msisdn) }
            val imageUploadSuccess = imageUploadDeferred.await()
            if(imageUploadSuccess){
                try {
                    val result = channelRepository.createAndUpdateChannel(_channelName.value.toString(), _channelDesc.value.toString(), _channelAvatar.value.toString(), msisdn)
                    _statusCreateChannel.value = result.code == 200
                    Log.e("test", "${result.data.msisdn}")
                    Log.e("test", "${result.data.channelAvatar}")
                    Log.e("test", "${result.data.channelName}")
                    Log.e("test", "${result.data.description}")
                    Log.e("test", "${result.data.id}")
                } catch (e:Exception){
                    e.printStackTrace()
                    _statusCreateChannel.value = false
                }
            } else _statusCreateChannel.value = false
        }
    }
}