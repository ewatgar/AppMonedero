package com.example.appmonedero.ui.login.usecase

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmonedero.data.model.Customer
import com.example.appmonedero.data.network.Resource
import com.example.appmonedero.data.repository.CustomerRepository
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    var username = MutableLiveData<String>()
    var pin = MutableLiveData<String>()

    private var state = MutableLiveData<LoginState>()

    fun getState(): MutableLiveData<LoginState> {
        return state
    }

    fun validateCredentials() {
        when {
            TextUtils.isEmpty(username.value) -> state.value = LoginState.UsernameEmptyError
            TextUtils.isEmpty(pin.value) -> state.value = LoginState.PinEmptyError
            pin.value?.toIntOrNull() == null -> state.value = LoginState.PinFormatError
            else -> {
                viewModelScope.launch {
                    state.value = LoginState.Loading(true)
                    val result = CustomerRepository.login(username.value!!, pin.value!!.toInt())
                    state.value = LoginState.Loading(false)
                    when(result){
                        Resource.Error -> state.value = LoginState.AuthenticationError
                        is Resource.Success<*> -> state.value = LoginState.Success(result.data as Customer)
                    }
                }
            }
        }
    }
}