package com.example.appmonedero.data.repository

import com.example.appmonedero.data.model.Account
import com.example.appmonedero.data.model.CountryCode
import com.example.appmonedero.data.model.Customer
import com.example.appmonedero.data.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
class CustomerRepository private constructor() {
    companion object {
        var dataset: MutableList<Customer> = mutableListOf()

        init {
            initDataset()
        }

        private fun initDataset(): MutableList<Customer> {

            val accountsList1 = arrayListOf<Account>(
                Account(CountryCode.FR, 3234, 2434, 25, 1443567890, 56000),
                Account(CountryCode.ES, 1234, 5678, 12, 9876543210, 1000),
                Account(CountryCode.AU, 5678, 1234, 45, 1234567890, 50)
            )

            val accountsList2 = arrayListOf<Account>(
                Account(CountryCode.US, 1223, 2342, 34, 6357901359, 435000),
                Account(CountryCode.UA, 9876, 4553, 67, 1357924680, 120),
                Account(CountryCode.US, 4321, 8765, 89, 2468013579, 40)
            )

            dataset.add(Customer("kiwi44", 1234, accountsList1))
            dataset.add(Customer("pato11", 7734, accountsList2))
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

        suspend fun getAccountsList(customerArgs: Customer): Resource{
            return withContext(Dispatchers.IO) {
                delay(2000)
                val customer = dataset.find { customerDataset -> customerDataset.username == customerArgs.username }
                val accountsList = customer!!.accountList
                when{
                    accountsList.isEmpty() -> Resource.Error
                    else -> Resource.Success(accountsList)
                }
            }
        }

        fun checkAccountExists(newAccount: Account):Boolean{
            return dataset.any { c -> c.accountList.contains(newAccount) }
        }

        fun createNewAccount(customerArgs:Customer, accountArgs:Account){
            dataset.forEach{ c -> if (c == customerArgs) c.accountList.add(accountArgs)}
        }

        fun depositMoney(customerArgs: Customer, accountArgs: Account, money: Int) {
            dataset.forEach{ c -> if (c == customerArgs) c.accountList.forEach { a -> if (a == accountArgs) a.balance+=money} }
        }

        fun withdrawMoney(customerArgs: Customer, accountArgs: Account, money: Int) {
            dataset.forEach{ c -> if (c == customerArgs) c.accountList.forEach { a -> if (a == accountArgs) a.balance-=money} }
        }



    }
}