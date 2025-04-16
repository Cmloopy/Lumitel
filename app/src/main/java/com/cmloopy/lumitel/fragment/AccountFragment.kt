package com.cmloopy.lumitel.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmloopy.lumitel.viewmodels.AccountFragmentViewModel
import com.cmloopy.lumitel.R

class AccountFragment : Fragment() {

    companion object {
        fun newInstance(msisdn: String): AccountFragment {
            val fragment = AccountFragment()
            val args = Bundle()
            args.putString("msisdn", msisdn)
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: AccountFragmentViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }
}