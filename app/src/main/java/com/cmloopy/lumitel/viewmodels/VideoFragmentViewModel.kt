package com.cmloopy.lumitel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmloopy.lumitel.data.models.category.Category
import com.cmloopy.lumitel.data.repository.CategoryRepository
import kotlinx.coroutines.launch

class VideoFragmentViewModel : ViewModel() {
    private val categoryRepository = CategoryRepository()

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    fun getCategory(msisdn: String) {
        viewModelScope.launch {
            try {
                val resultList = categoryRepository.getAllCategory(msisdn = msisdn)
                _categories.value = resultList
            } catch (e:Exception){
                e.printStackTrace()
                _categories.value = emptyList()
            }
        }
    }
}