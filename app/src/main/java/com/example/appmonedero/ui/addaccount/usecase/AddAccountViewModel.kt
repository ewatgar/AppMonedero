package com.example.appmonedero.ui.addaccount.usecase

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmonedero.data.model.Account
import com.example.appmonedero.data.model.CountryCode
import com.example.appmonedero.data.model.Customer
import com.example.appmonedero.data.repository.CustomerRepository
import com.example.appmonedero.data.repository.CustomerRepository.Companion.checkAccountExists

class AddAccountViewModel: ViewModel() {
    var customer = MutableLiveData<Customer>()
    var countryCode = MutableLiveData<CountryCode>()
    var entidad = MutableLiveData<String>()
    var oficina = MutableLiveData<String>()
    var dc = MutableLiveData<String>()
    var accountNum = MutableLiveData<String>()

    private var state = MutableLiveData<AddAccountState>()

    fun getState(): MutableLiveData<AddAccountState> {
        return state
    }

    fun getNewAccount() : Account{
        return Account(countryCode.value!!,entidad.value!!.toInt(),oficina.value!!.toInt(),dc.value!!.toInt(),accountNum.value!!.toLong(),0)
    }

    fun verifyNewAccount() {
        val regex4Dig = Regex("\\d{4}")
        val regex2Dig= Regex("\\d{2}")
        val regex10Dig = Regex("\\d{10}")

        when{
            TextUtils.isEmpty(entidad.value) -> state.value = AddAccountState.EntidadEmptyError
            TextUtils.isEmpty(oficina.value) -> state.value = AddAccountState.OficinaEmptyError
            TextUtils.isEmpty(dc.value) -> state.value = AddAccountState.DcEmptyError
            TextUtils.isEmpty(accountNum.value) -> state.value = AddAccountState.AccountNumEmptyError
            !regex4Dig.matches(entidad.value!!) -> state.value = AddAccountState.EntidadFormatError
            !regex4Dig.matches(oficina.value!!) -> state.value = AddAccountState.OficinaFormatError
            !regex2Dig.matches(dc.value!!) -> state.value = AddAccountState.DcFormatError
            !regex10Dig.matches(accountNum.value!!) -> state.value = AddAccountState.AccountNumFormatError
            checkAccountExists(getNewAccount()) -> state.value = AddAccountState.AccountExistsError
            else -> state.value = AddAccountState.Success
        }
    }

    fun createNewAccount(){
        CustomerRepository.createNewAccount(customer.value!!,getNewAccount())
    }


}