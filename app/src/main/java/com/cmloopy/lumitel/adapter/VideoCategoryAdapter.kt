package com.cmloopy.lumitel.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cmloopy.lumitel.data.models.category.Category
import com.cmloopy.lumitel.fragment.VideoCateFragment
import com.cmloopy.lumitel.fragment.ShortCateFragment

class VideoCategoryAdapter(fragment: Fragment, private var categories: List<Category>, private val msisdn: String): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): Fragment {
        val category = categories[position]
        if(category.cateName == "Short"){
            return sendIdCate(category.id,ShortCateFragment())
        }
        return sendIdCate(category.id, VideoCateFragment())
    }

    private fun sendIdCate(idCategory: Int, fragment: Fragment): Fragment{
        val setupFragment = fragment
        setupFragment.arguments = Bundle().apply {
            putInt("idCategory", idCategory)
            putString("msisdn", msisdn)
        }
        return fragment
    }
}