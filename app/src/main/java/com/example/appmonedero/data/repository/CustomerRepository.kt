package com.example.appmonedero.data.repository

import com.example.appmonedero.data.model.Account
import com.example.appmonedero.data.model.CountryCode
import com.example.appmonedero.data.model.Customer
import com.example.appmonedero.data.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Exception

class CustomerRepository private constructor() {
    companion object {
        var dataset: MutableList<Customer> = mutableListOf()

        init {
            initDataset()
        }

        private fun initDataset(): MutableList<Customer> {
            val accountsList1 = arrayListOf<Account>(
                Account(CountryCode.ES, 1234, 5678, 12, 9876543210),
                Account(CountryCode.AU, 5678, 1234, 45, 1234567890)
            )

            val accountsList2 = arrayListOf<Account>(
                Account(CountryCode.UA, 9876, 5432, 67, 1357924680),
                Account(CountryCode.US, 4321, 8765, 89, 2468013579)
            )

            dataset.add(Customer("kiwi49", 1234, accountsList1))
            dataset.add(Customer("pato111", 7734, accountsList2))
            return dataset
        }

        suspend fun login(username: String, pin: Int): Resource {
            return withContext(Dispatchers.IO) {
                delay(2000)
                val customer = dataset.find { customer -> customer.username == username && customer.pin == pin }
                when(customer){
                    null -> Resource.Error
                    else -> Resource.Success(customer)
                }
            }
        }

        suspend fun getAccountsList(customer: Customer): Resource{
            return withContext(Dispatchers.IO) {
                delay(2000)
                val customerUpdated = dataset.find { customerDataset -> customerDataset.username == customer.username }
                val accountsList = customerUpdated!!.accountList
                when{
                    accountsList.isEmpty() -> Resource.Error
                    else -> Resource.Success(accountsList)
                }
            }
        }
    }
}