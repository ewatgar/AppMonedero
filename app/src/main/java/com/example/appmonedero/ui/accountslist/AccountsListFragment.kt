package com.example.appmonedero.ui.accountslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.appmonedero.R
import com.example.appmonedero.adapter.AccountsListAdapter
import com.example.appmonedero.databinding.FragmentAccountsListBinding
import com.example.appmonedero.databinding.FragmentLoginBinding
import com.example.appmonedero.ui.accountslist.usecase.AccountsListViewModel
import com.example.appmonedero.ui.login.usecase.LoginViewModel

class AccountsListFragment : Fragment() {

    private var _binding: FragmentAccountsListBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: AccountsListViewModel by viewModels()

    private lateinit var accountsListAdapter: AccountsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountsListBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewmodel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initRecycler(){

    }

}