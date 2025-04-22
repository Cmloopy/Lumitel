package com.cmloopy.lumitel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmloopy.lumitel.data.models.channel.Channel
import com.cmloopy.lumitel.data.models.comment.Comment
import com.cmloopy.lumitel.data.repository.ChannelRepository
import com.cmloopy.lumitel.data.repository.CommentRepository
import kotlinx.coroutines.launch


class BottomSheetCommentViewModel: ViewModel() {
    private val commentRepository = CommentRepository()
    private val channelRepository = ChannelRepository()

    private val _listComment = MutableLiveData<List<Comment>>()
    private val _channel = MutableLiveData<Channel>()
    val listComment : LiveData<List<Comment>> get() = _listComment
    val channel : LiveData<Channel> get() = _channel
    fun getChannelInfo(msisdn:String){
        viewModelScope.launch {
            try {
                val resultList = channelRepository.getInfoMyChannel(msisdn = msisdn)
                _channel.value = resultList.data
            }catch (e:Exception){
                e.printStackTrace()
                _channel.value = Channel(-1,"","","","",null, "", 0,0,0,0L,0,0,"", "")
            }
        }
    }
    fun getCommentVideo(msisdn:String, contentId: Int){
        viewModelScope.launch {
            try {
                val result = commentRepository.getCommentVideo(contentId,msisdn)
                _listComment.value = result
            } catch (e:Exception){
                e.printStackTrace()
                _listComment.value = emptyList()
            }
        }
    }
}