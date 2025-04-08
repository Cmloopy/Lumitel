package com.cmloopy.lumitel.data.repository

import com.cmloopy.lumitel.data.api.CategoryApi
import com.cmloopy.lumitel.data.api.retrofit.RetrofitClient
import com.cmloopy.lumitel.data.models.category.Category
import com.cmloopy.lumitel.data.models.category.CategoryResponse

class CategoryRepository {

    private val msisdn = "+84338363931"
    private val timestamp = System.currentTimeMillis().toString()

    private val listCate = mutableListOf<Category>()

    private var apiCategoryService = RetrofitClient.instance.create(CategoryApi::class.java)

    suspend fun getAllCategory(): List<Category>{
        listCate.add(Category(-1,"Hot","", "",""))
        val result = apiCategoryService.getAllCategory(msisdn,"", timestamp, "")
        result.data.forEach{
            listCate.add(it)
        }
        listCate[1].cateName = "New"
        return listCate
    }
}