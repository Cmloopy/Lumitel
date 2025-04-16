package com.cmloopy.lumitel.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cmloopy.lumitel.data.models.category.Category
import com.cmloopy.lumitel.fragment.VideoCateFragment
import com.cmloopy.lumitel.fragment.ShortCateFragment

class VideoCategoryAdapter(fragment: Fragment, private var categories: List<Category>, private val msisdn: String?): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): Fragment {
        val category = categories[position]
        if(category.cateName == "Short"){
            return ShortCateFragment.newInstance(category.id, msisdn)
        }
        return VideoCateFragment.newInstance(category.id, msisdn)
    }
}