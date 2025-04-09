package com.cmloopy.lumitel.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cmloopy.lumitel.data.models.category.Category
import com.cmloopy.lumitel.fragment.AllVideoFragment

class VideoCategoryAdapter(fragment: Fragment, private var categories: List<Category>): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): Fragment {
        val category = categories[position]
        return sendIdCate(category.id, AllVideoFragment())
    }

    private fun sendIdCate(idCategory: Int, fragment: Fragment): Fragment{
        val setupFragment = fragment
        setupFragment.arguments = Bundle().apply {
            putInt("idCategory", idCategory)
        }
        return fragment
    }
}