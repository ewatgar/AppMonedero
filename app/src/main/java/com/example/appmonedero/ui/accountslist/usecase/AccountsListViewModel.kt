package com.example.appmonedero.ui.accountslist.usecase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmonedero.data.model.Account
import com.example.appmonedero.data.model.Customer
import com.example.appmonedero.data.network.Resource
import com.example.appmonedero.data.repository.CustomerRepository
import kotlinx.coroutines.launch

class AccountsListViewModel: ViewModel() {
    var customer = MutableLiveData<Customer>()

    private var state = MutableLiveData<AccountsListState>()

    fun getState(): MutableLiveData<AccountsListState> {
        return state
    }

    fun getAccountsList() {
        viewModelScope.launch{
            state.value = AccountsListState.Loading(true)
            val result = CustomerRepository.getAccountsList(customer.value!!)
            state.value = AccountsListState.Loading(false)
            when(result){
                Resource.Error -> state.value = AccountsListState.NoDataError
                is Resource.Success<*> -> state.value = AccountsListState.Success(result.data as ArrayList<Account>)
            }
        }

    }

}