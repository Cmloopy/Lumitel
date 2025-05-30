package com.cmloopy.lumitel.data.repository

import com.cmloopy.lumitel.data.api.CategoryApi
import com.cmloopy.lumitel.data.api.retrofit.RetrofitClient
import com.cmloopy.lumitel.data.models.category.Category

class CategoryRepository {

    private val timestamp = System.currentTimeMillis().toString()

    private var apiCategoryService = RetrofitClient.instance.create(CategoryApi::class.java)

    suspend fun getAllCategory(msisdn: String): List<Category>{
        val listCate = mutableListOf<Category>()
        listCate.add(Category(-1,"Hot","", "",""))
        val result = apiCategoryService.getAllCategory(msisdn,"", timestamp, "")
        result.data.forEach{
            listCate.add(it)
        }
        listCate[1].cateName = "New"
        return listCate
    }
}