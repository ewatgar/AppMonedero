package com.example.appmonedero.ui.depositmoney.usecase

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmonedero.data.model.Account
import com.example.appmonedero.data.model.Customer
import com.example.appmonedero.data.repository.FraudControlRepository

class DepositMoneyViewModel: ViewModel() {
    var customer = MutableLiveData<Customer>()
    var account = MutableLiveData<Account>()
    var money = MutableLiveData<String>()

    private var state = MutableLiveData<DepositMoneyState>()

    fun getState(): MutableLiveData<DepositMoneyState> {
        return state
    }

    fun verifyDeposit() {
        when{
            TextUtils.isEmpty(money.value) -> state.value = DepositMoneyState.MoneyEmptyError
            money.value?.toIntOrNull() == null -> state.value = DepositMoneyState.MoneyFormatError
            money.value!!.toInt() > FraudControlRepository.FRAUD_LIMIT*2 -> state.value = DepositMoneyState.MoneyDoubleOverLimit
            money.value!!.toInt() > FraudControlRepository.FRAUD_LIMIT -> state.value = DepositMoneyState.MoneyOverLimit
            else -> state.value = DepositMoneyState.Success
        }
    }
}