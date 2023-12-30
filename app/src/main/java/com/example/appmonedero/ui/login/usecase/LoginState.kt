package com.example.appmonedero.ui.login.usecase

import com.example.appmonedero.data.model.Customer

sealed class LoginState {
    data object UsernameEmptyError : LoginState()
    data object PinEmptyError : LoginState()
    data object PinFormatError : LoginState()
    data object AuthenticationError : LoginState()
    data class Loading(var active: Boolean) : LoginState()
    data class Success(var customer: Customer) : LoginState()
}