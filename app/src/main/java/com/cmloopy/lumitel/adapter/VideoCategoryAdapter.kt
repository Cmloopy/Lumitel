package com.cmloopy.lumitel.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cmloopy.lumitel.data.models.category.Category
import com.cmloopy.lumitel.fragment.AccountFragment
import com.cmloopy.lumitel.fragment.HotVideoFragment

class VideoCategoryAdapter(fragment: Fragment, private var categories: List<Category>): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): Fragment {
        val category = categories[position]
        return HotVideoFragment()
    }
}