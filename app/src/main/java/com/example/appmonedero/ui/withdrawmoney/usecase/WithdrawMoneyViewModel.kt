package com.example.appmonedero.ui.withdrawmoney.usecase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WithdrawMoneyViewModel : ViewModel() {
    var money = MutableLiveData<String>()
}