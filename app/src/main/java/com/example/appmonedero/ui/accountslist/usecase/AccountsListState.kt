package com.example.appmonedero.ui.accountslist.usecase

import com.example.appmonedero.data.model.Account

sealed class AccountsListState {
    data object NoDataError: AccountsListState()
    data class Success(val accountsList: ArrayList<Account>): AccountsListState()
    data class Loading(val active: Boolean):AccountsListState()
}
