package com.cmloopy.lumitel.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cmloopy.lumitel.fragment.AccountFragment
import com.cmloopy.lumitel.fragment.HomeFragment
import com.cmloopy.lumitel.fragment.PhoneFragment

class VideoCategoryAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    private val fragments = listOf(
        AccountFragment(),
        HomeFragment(),
        PhoneFragment(),
        AccountFragment()
    )
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}