package com.example.appmonedero.ui.withdrawmoney.usecase

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmonedero.data.model.Account
import com.example.appmonedero.data.model.Customer
import com.example.appmonedero.data.model.Transaction
import com.example.appmonedero.data.repository.CustomerRepository
import com.example.appmonedero.data.repository.FraudControlRepository

class WithdrawMoneyViewModel : ViewModel() {
    var customer = MutableLiveData<Customer>()
    var account = MutableLiveData<Account>()
    var money = MutableLiveData<String>()

    private var state = MutableLiveData<WithdrawMoneyState>()

    fun getState(): MutableLiveData<WithdrawMoneyState> {
        return state
    }

    fun verifyWithdraw() {
        when{
            TextUtils.isEmpty(money.value) -> state.value = WithdrawMoneyState.MoneyEmptyError
            money.value?.toIntOrNull() == null -> state.value = WithdrawMoneyState.MoneyFormatError
            money.value!!.toInt() > account.value!!.balance -> state.value = WithdrawMoneyState.NotEnoughBalanceError
            money.value!!.toInt() > FraudControlRepository.FRAUD_LIMIT*2 -> state.value = WithdrawMoneyState.MoneyDoubleOverLimit
            money.value!!.toInt() > FraudControlRepository.FRAUD_LIMIT -> state.value = WithdrawMoneyState.MoneyOverLimit
            else -> state.value = WithdrawMoneyState.Success
        }
    }

    fun withdrawMoney(){
        CustomerRepository.withdrawMoney(customer.value!!,account.value!!,money.value!!.toInt())
    }

    fun controlTransaction(cancelled: Boolean){
        val transaction = Transaction(customer.value!!, account.value!!, false, money.value!!.toInt(), cancelled)
        FraudControlRepository.addTransaction(transaction)
    }
}