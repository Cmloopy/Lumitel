package com.cmloopy.lumitel.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.databinding.ActivityMainBinding
import com.cmloopy.lumitel.fragment.AccountFragment
import com.cmloopy.lumitel.fragment.VideoFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var msisdn = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding.bottomNavigationView.selectedItemId = R.id.frg_video
        val mainFragment = VideoFragment.newInstance(msisdn)
        replaceFragment(mainFragment)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.frg_video -> {
                    val fragment = VideoFragment.newInstance(msisdn)
                    replaceFragment(fragment)
                    true
                }
                R.id.frg_account -> {
                    val fragment = AccountFragment.newInstance(msisdn)
                    replaceFragment(fragment)
                    true
                }
                else -> false
            }
        }
    }
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frm_container,fragment).commit()
    }
}